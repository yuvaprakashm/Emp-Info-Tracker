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
	private EmployeeRepository employeeRepo;
	@Autowired
	private EmployeeMapper employeeMapper;
	@Autowired
	private DepartmentRepository departmentRepo;

	@Override
	public Page<EmployeeVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy,
			String searchText) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Utility.sortByValues(sortBy)));
		Specification<Employee> joins = CommonSpecification.searchEmployee(searchText, filterBy);
		Page<Employee> page = employeeRepo.findAll(joins, pageable);
		List<EmployeeVo> employeeVoList = employeeMapper.toDtos(page.getContent());
		return new PageImpl<>(employeeVoList, pageable, page.getTotalElements());
	}

	@Override
	public List<EmployeeVo> findAll() {
		return employeeMapper.toDtos(employeeRepo.findAll());
	}

	@Override
	public EmployeeVo findById(Long id) {
		Employee employee = employeeRepo.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND + id));
		return employeeMapper.toDto(employee);
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
			
			DepartmentVo departmentVo = employeeVo.getDepartment();
			Department department = new Department();
			department.setDeptName(departmentVo.getDeptName());
			department.setBudget(departmentVo.getBudget());
			department.setDeptContactNumber(departmentVo.getDeptContactNumber());
			department.setEmailAddress(departmentVo.getEmailAddress());
			department.setCreatedDate(departmentVo.getCreatedDate());
			department.setStatus(departmentVo.getStatus());
			department = departmentRepo.save(department);
			
			employee.setDepartment(department);
			employee = employeeRepo.save(employee);
			
			 
			List<AddressVo> addressVos = employeeVo.getAddresses();
			for (AddressVo addressVo : addressVos) {
				Address address = new Address();
				address.setStreet(addressVo.getStreet());
				address.setCity(addressVo.getCity());
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
			return employeeMapper.toDto(employee);
		} catch (Exception e) {
			throw new RuntimeException(FAILED_ADD_EMP + e.getMessage());
		}
	}

	@Transactional
	@Override
	public EmployeeVo update(EmployeeVo employeeVo, Long id, boolean partialUpdate) {
		Employee existingEmployee = employeeRepo.findById(id)
				.orElseThrow(() -> new RuntimeException(EMPLOYEE_NOT_FOUND + id));

		if (partialUpdate) {
			if (employeeVo.getFirstName() != null)
				existingEmployee.setFirstName(employeeVo.getFirstName());
			if (employeeVo.getLastName() != null)
				existingEmployee.setLastName(employeeVo.getLastName());
			if (employeeVo.getEmail() != null)
				existingEmployee.setEmail(employeeVo.getEmail());
			if (employeeVo.getSalary() != null)
				existingEmployee.setSalary(employeeVo.getSalary());
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

		Employee updatedEmployee = employeeRepo.save(existingEmployee);
		return employeeMapper.toDto(updatedEmployee);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		findById(id);
		employeeRepo.deleteById(id);
	}

	@Transactional
	@Override
	public int active(Long id) {
		return employeeRepo.updateStatus(GenericStatus.ACTIVE, id);
	}

	@Transactional
	@Override
	public int deactive(Long id) {
		return employeeRepo.updateStatus(GenericStatus.DEACTIVE, id);
	}

	@Override
	public String generateCsvContent() {
		StringWriter writer = new StringWriter();
		try (@SuppressWarnings("deprecation")
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(EMPLOYEE_HEADER))) {
			List<EmployeeVo> employeeList = findAll();
			for (EmployeeVo employee : employeeList) {
				csvPrinter.printRecord(employee.getId(), employee.getFirstName(), employee.getLastName(),
						employee.getAge(), employee.getEmail(), employee.getGender(), employee.getSalary(),
						employee.getStatus(), employee.getCreatedDate(), employee.getContactNumber(),
						employee.getDateOfBirth(), employee.getHireDate(), employee.getJobTitle());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

}
