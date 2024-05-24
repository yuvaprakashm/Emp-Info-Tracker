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
import net.texala.employee.mapper.EmployeeMapper;
import net.texala.employee.model.Employee;
import net.texala.employee.repository.EmployeeRepository;
import net.texala.employee.vo.EmployeeVo;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepo;
	@Autowired
	private DepartmentMapper departmentMapper;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public Page<DepartmentVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy,
			String searchText) {
		final Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Utility.sortByValues(sortBy)));
		final Specification<Department> joins = CommonSpecification.searchDepartment(searchText, filterBy);
		final Page<Department> page = departmentRepo.findAll(joins, pageable);
		return new PageImpl<>(departmentMapper.toDtos(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	public List<DepartmentVo> findAll() {
		return departmentMapper.toDtos(departmentRepo.findAll());
	}

	@Override
	public DepartmentVo findById(Long id) {
		Department department = departmentRepo.findById(id)
				.orElseThrow(() -> new DepartmentNotFoundException(DEPARTMENT_NOT_FOUND + id));
		return departmentMapper.toDto(department);
	}

	@Override
	@Transactional
	public DepartmentVo add(DepartmentVo departmentVo) {
		try {
			Department department = departmentMapper.toEntity(departmentVo);
			department = departmentRepo.save(department);
			Employee employee = null;
			for (EmployeeVo employeeVo : departmentVo.getEmployees()) {
				employee = employeeMapper.toEntity(employeeVo);
				employee.setDepartment(department);
				employee = employeeRepo.save(employee);
			}
			department.setEmployee(employee);
			return departmentMapper.toDto(department);
		} catch (Exception e) {
			throw new RuntimeException(FAILED_ADD_DEPT + e.getMessage());
		}
	}

	@Transactional
	@Override
	public DepartmentVo update(DepartmentVo departmentVo, Long id, boolean partialUpdate) {
		Department existingDepartment = departmentRepo.findById(id)
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
		Department updatedDepartment = departmentRepo.save(existingDepartment);
		entityManager.flush();
		return departmentMapper.toDto(updatedDepartment);
	}

	@Override
	public void delete(Long id) {
		findById(id);
		departmentRepo.deleteById(id);

	}

	@Transactional
	@Override
	public int active(Long id) {
		return departmentRepo.updateStatus(GenericStatus.ACTIVE, id);
	}

	@Transactional
	@Override
	public int deactive(Long id) {
		return departmentRepo.updateStatus(GenericStatus.DEACTIVE, id);
	}

	@Override
	public String generateCsvContent() {
		StringWriter writer = new StringWriter();
		try (@SuppressWarnings("deprecation")
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(DEPARTMENT_HEADER))) {
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
