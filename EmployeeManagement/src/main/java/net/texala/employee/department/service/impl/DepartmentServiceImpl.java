package net.texala.employee.department.service.impl;

import static net.texala.employee.constants.Constants.*;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.texala.employee.Specification.CommonSpecification;
import net.texala.employee.Util.Utility;
import net.texala.employee.department.mapper.DepartmentMapper;
import net.texala.employee.department.model.Department;
import net.texala.employee.department.repository.DepartmentRepository;
import net.texala.employee.department.service.DepartmentService;
import net.texala.employee.department.vo.DepartmentVo;
import net.texala.employee.enums.GenericStatus;
import net.texala.employee.exception.Exception.DepartmentNotFoundException;
import net.texala.employee.model.Employee;
import net.texala.employee.repository.EmployeeRepository;
import net.texala.employee.vo.EmployeeVo;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository repo;
	@Autowired
	private DepartmentMapper mapper;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private EmployeeRepository employeeRepo;

	@Override
	public Page<DepartmentVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy,
			String searchText) {
		final Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Utility.sortByValues(sortBy)));
		final Specification<Department> joins = CommonSpecification.searchDepartment(searchText, filterBy);
		final Page<Department> page = repo.findAll(joins, pageable);
		return new PageImpl<>(mapper.toDtos(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	public Department findById(Long id) {
		return repo.findById(id).orElseThrow(() -> new DepartmentNotFoundException(DEPARTMENT_NOT_FOUND + id));
	}
 
	@Override
	@Transactional
	public DepartmentVo add(DepartmentVo departmentVo) {
	    try {
	        Department department = new Department();
	        department.setDeptName(departmentVo.getDeptName());
	        department.setStatus(departmentVo.getStatus());
	        department.setCreatedDate(departmentVo.getCreatedDate());
	        department.setDeptContactNumber(departmentVo.getDeptContactNumber());
	        department.setEmailAddress(departmentVo.getEmailAddress());
	        department.setBudget(departmentVo.getBudget());

	        department = repo.save(department);

	        List<EmployeeVo> employeeVos = departmentVo.getEmployees();
	        Employee employee = null;
	        for (EmployeeVo employeeVo : employeeVos) {
	            employee = new Employee();
	            employee.setFirstName(employeeVo.getFirstName());
	            employee.setLastName(employeeVo.getLastName());
	            employee.setAge(employeeVo.getAge());
	            employee.setEmail(employeeVo.getEmail());
	            employee.setSalary(employeeVo.getSalary());
	            employee.setGender(employeeVo.getGender());
	            employee.setStatus(employeeVo.getStatus());
	            employee.setContactNumber(employeeVo.getContactNumber());
	            employee.setDateOfBirth(employeeVo.getDateOfBirth());
	            employee.setHireDate(employeeVo.getHireDate());
	            employee.setJobTitle(employeeVo.getJobTitle());

	            employee.setDepartment(department);

	            employee = employeeRepo.save(employee);
	        }

	        if (employee != null) {
	            department.setEmployee(employee);
	        }

	        return mapper.toDto(department);
	    } catch (Exception e) {
 	        throw new RuntimeException("Failed to add department: " + e.getMessage());
	    }
	}

	 


	@Transactional
	@Override
	public DepartmentVo update(DepartmentVo departmentVo, Long id, boolean partialUpdate) {
		Department existingDepartment = repo.findById(id)
				.orElseThrow(() -> new DepartmentNotFoundException(DEPARTMENT_NOT_FOUND + id));
		if (partialUpdate) {
			if (departmentVo.getDeptName() != null) {
				existingDepartment.setDeptName(departmentVo.getDeptName());
			}
			if (departmentVo.getDeptContactNumber() != null) {
				existingDepartment.setDeptContactNumber(departmentVo.getDeptContactNumber());
			}
			if (departmentVo.getEmailAddress() != null) {
				existingDepartment.setEmailAddress(departmentVo.getEmailAddress());
			}
		} else {
			existingDepartment.setDeptName(departmentVo.getDeptName());
			existingDepartment.setStatus(departmentVo.getStatus());
			existingDepartment.setCreatedDate(departmentVo.getCreatedDate());
			existingDepartment.setDeptContactNumber(departmentVo.getDeptContactNumber());
			existingDepartment.setEmailAddress(departmentVo.getEmailAddress());
			existingDepartment.setBudget(departmentVo.getBudget());
		}
		Department updatedDepartment = repo.save(existingDepartment);
		entityManager.flush();
		return mapper.toDto(updatedDepartment);
	}

	@Transactional
	@Override
	public int active(Long id) {
		return repo.updateStatus(GenericStatus.ACTIVE, id);
	}

	@Transactional
	@Override
	public int deactive(Long id) {
		return repo.updateStatus(GenericStatus.DEACTIVE, id);
	}

	@Override
	public void delete(Long id) {
		findById(id);
		repo.deleteById(id);

	}

	@Override
	public List<DepartmentVo> findAll() {
		return mapper.toDtos(repo.findAll());
	}

	@Override
	public String generateCsvContent() {
		StringWriter writer = new StringWriter();
		try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(DEPARTMENT_HEADER))) {
			List<DepartmentVo> departmentList = findAll();
			if (departmentList != null && !departmentList.isEmpty()) {
				for (DepartmentVo department : departmentList) {
					csvPrinter.printRecord(department.getDeptId(), department.getDeptName(),
							department.getCreatedDate(), department.getStatus(), department.getDeptContactNumber(),
							department.getEmailAddress(), department.getBudget());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

}
