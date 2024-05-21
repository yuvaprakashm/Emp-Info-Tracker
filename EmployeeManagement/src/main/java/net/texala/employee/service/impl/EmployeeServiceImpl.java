package net.texala.employee.service.impl;

import static net.texala.employee.constants.Constants.*;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
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
import net.texala.employee.address.model.Address;
import net.texala.employee.address.repository.AddressRepository;
import net.texala.employee.address.vo.AddressVo;
import net.texala.employee.department.model.Department;
import net.texala.employee.department.repository.DepartmentRepository;
import net.texala.employee.department.vo.DepartmentVo;
import net.texala.employee.enums.GenericStatus;
import net.texala.employee.exception.Exception.EmployeeNotFoundException;
import net.texala.employee.mapper.EmployeeMapper;
import net.texala.employee.model.Employee;
import net.texala.employee.repository.EmployeeRepository;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.vo.EmployeeVo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private AddressRepository addressRepo;
	@Autowired
	private EmployeeRepository repo;
	@Autowired
	private EmployeeMapper mapper;
	@Autowired
	private DepartmentRepository departmentRepo;
	@Override
	public Page<EmployeeVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy,
			String searchText) {
		final Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Utility.sortByValues(sortBy)));
		final Specification<Employee> joins = CommonSpecification.searchEmployee(searchText, filterBy);
		final Page<Employee> page = repo.findAll(joins, pageable);
		return new PageImpl<>(mapper.toDtos(page.getContent()), pageable, page.getTotalElements());

	}

	@Override
	public Employee findById(Long id) {
		return repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND + id));
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
	public List<EmployeeVo> findAll() {
		return mapper.toDtos(repo.findAll());
	}

	@Override
	public EmployeeVo update(EmployeeVo employeeVo, Long id, boolean partialUpdate) {
		Employee existingEmployee = repo.findById(id).orElseThrow(() -> new RuntimeException(EMPLOYEE_NOT_FOUND + id));

		if (partialUpdate) {
			if (employeeVo.getFirstName() != null) {
				existingEmployee.setFirstName(employeeVo.getFirstName());
			}
			if (employeeVo.getLastName() != null) {
				existingEmployee.setLastName(employeeVo.getLastName());
			}
			if (employeeVo.getEmail() != null) {
				existingEmployee.setEmail(employeeVo.getEmail());
			}
			if (employeeVo.getSalary() != null) {
				existingEmployee.setSalary(employeeVo.getSalary());
			}

		} else {

			existingEmployee.setFirstName(employeeVo.getFirstName());
			existingEmployee.setLastName(employeeVo.getLastName());
			existingEmployee.setAge(employeeVo.getAge());
			existingEmployee.setEmail(employeeVo.getEmail());
			existingEmployee.setGender(employeeVo.getGender());
			existingEmployee.setSalary(employeeVo.getSalary());
			existingEmployee.setStatus(employeeVo.getStatus());
			existingEmployee.setContactNumber(employeeVo.getContactNumber());
			existingEmployee.setDateOfBirth(employeeVo.getDateOfBirth());
			existingEmployee.setHireDate(employeeVo.getHireDate());
			existingEmployee.setJobTitle(employeeVo.getJobTitle());

		}

		Employee updatedEmployee = repo.save(existingEmployee);
		return mapper.toDto(updatedEmployee);
	}

	@Override
	public String generateCsvContent() {
		StringWriter writer = new StringWriter();
		try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(EMPLOYEE_HEADER))) {

			List<EmployeeVo> employeeList = findAll();
			if (employeeList != null && !employeeList.isEmpty()) {
				for (EmployeeVo employee : employeeList) {
					csvPrinter.printRecord(employee.getId(), employee.getFirstName(), employee.getLastName(),
							employee.getAge(), employee.getEmail(), employee.getGender(), employee.getSalary(),
							employee.getStatus(), employee.getCreatedDate(), employee.getContactNumber(),
							employee.getDateOfBirth(), employee.getHireDate(), employee.getJobTitle());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

 
	@Override
	@Transactional
	public EmployeeVo add(EmployeeVo employeeVo) {
	    try {
	        Employee employee = new Employee();
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

	        employee = repo.save(employee);

	        DepartmentVo departmentVo = employeeVo.getDepartment();
	        Department department = new Department();
	        department.setDeptName(departmentVo.getDeptName());
	        department.setBudget(departmentVo.getBudget());
	        department.setDeptContactNumber(departmentVo.getDeptContactNumber());
	        department.setEmailAddress(departmentVo.getEmailAddress());
	        department.setCreatedDate(departmentVo.getCreatedDate());
	        department.setStatus(departmentVo.getStatus());
	        department.setEmployee(employee);
	        departmentRepo.save(department);

	        employee.setDepartment(department);

	        employee = repo.save(employee);

	        List<AddressVo> addressVos = employeeVo.getAddresses();
	        for (AddressVo addressVo : addressVos) {
	            Address address = new Address();
	            address.setStreet(addressVo.getStreet());
	            address.setCity(addressVo.getCity());
	            address.setStreet(addressVo.getStreet());
	            address.setZipcode(addressVo.getZipcode());
	            address.setCreatedDate(addressVo.getCreatedDate());
	            address.setState(addressVo.getState());
	            address.setStatus(addressVo.getStatus());
	            address.setDoorNumber(addressVo.getDoorNumber());
	            address.setCountry(addressVo.getCountry());
	            address.setAddressType(addressVo.getAddressType());
	            address.setLandMark(addressVo.getLandMark());
	            address.setEmployee(employee);
	            addressRepo.save(address);
	        }

	        return mapper.toDto(employee);
	    } catch (Exception e) {
 	        throw new RuntimeException("Failed to add employee: " + e.getMessage());
	    }
	}



}