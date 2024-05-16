package net.texala.employee.service.impl;

import static net.texala.employee.constants.Constants.*;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.BeanUtils;
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
import net.texala.employee.address.vo.AddressVo;
import net.texala.employee.department.model.Department;
import net.texala.employee.department.service.DepartmentService;
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
	private EmployeeRepository repo;
	@Autowired
	private EmployeeMapper mapper;
	@Autowired
	private DepartmentService service;
	@Override
	public Page<EmployeeVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy,
			String searchText) {
		final Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Utility.sortByValues(sortBy)));
		final Specification<Employee> joins = CommonSpecification.searchEmployee(searchText, filterBy);
		final Page<Employee> page = repo.findAll(joins, pageable);
		//return new PageImpl<>(mapper.toDtos(page.getContent()), pageable, page.getTotalElements());
		return null;
	}

	@Override
	public Employee findById(Long id) {
		return repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND + id));
	}

//	@Override
//	public EmployeeVo add(EmployeeVo employeeVo) {
//		Employee employee = new Employee();
//		BeanUtils.copyProperties(employeeVo, employee);
//		return mapper.toDto(repo.save(employee));
//	}
//	 @Override
//	    public EmployeeVo add(EmployeeVo employeeVo) {
//	        // Create an Employee entity from the EmployeeVo
//	        Employee employee = new Employee();
//	        employee.setFirstName(employeeVo.getFirstName());
//	        employee.setLastName(employeeVo.getLastName());
//	        employee.setAge(employeeVo.getAge());
//	        employee.setEmail(employeeVo.getEmail());
//	        // Set other employee attributes similarly
//
//	        // Create Address entities from the addresses in the EmployeeVo
//	        List<Address> addresses = new ArrayList<>();
//	        if (employeeVo.getAddresses() != null) {
//	            for (AddressVo addressVo : employeeVo.getAddresses()) {
//	                Address address = new Address();
//	                address.setStreet(addressVo.getStreet());
//	                address.setCity(addressVo.getCity());
//	                address.setState(addressVo.getState());
//	                // Set other address attributes similarly
//	                address.setEmployee(employee); // Associate the address with the employee
//	                addresses.add(address);
//	            }
//	        }
//
//	        // Associate the addresses with the employee
//	        employee.setAddresses(addresses);
//
//	        // Save the employee entity (this should cascade to save addresses as well)
//	        Employee savedEmployee = repo.save(employee);
//
//	        // Convert the saved employee entity back to EmployeeVo and return it
//	        return convertToEmployeeVo(savedEmployee);
//	    }
//
//	    // Convert Employee entity to EmployeeVo
//	    private EmployeeVo convertToEmployeeVo(Employee employee) {
//	        EmployeeVo employeeVo = new EmployeeVo();
//	        employeeVo.setId(employee.getId());
//	        employeeVo.setFirstName(employee.getFirstName());
//	        employeeVo.setLastName(employee.getLastName());
//	        // Set other attributes similarly
//	        
//	        // Convert addresses to AddressVo array
//	        List<Address> addresses = employee.getAddresses();
//	        if (addresses != null) {
//	            AddressVo[] addressVos = new AddressVo[addresses.size()];
//	            for (int i = 0; i < addresses.size(); i++) {
//	                Address address = addresses.get(i);
//	                AddressVo addressVo = new AddressVo();
//	                addressVo.setStreet(address.getStreet());
//	                addressVo.setCity(address.getCity());
//	                addressVo.setState(address.getState());
//	                // Set other attributes similarly
//	                addressVos[i] = addressVo;
//	            }
//	            employeeVo.setAddresses(addressVos);
//	        }
//	        
//	        return employeeVo;
//	    }
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
		return null;//mapper.toDtos(repo.findAll());
	}

//	@Override
//	public EmployeeVo update(EmployeeVo employeeVo, Long id, boolean partialUpdate) {
//		Employee existingEmployee = repo.findById(id).orElseThrow(() -> new RuntimeException(EMPLOYEE_NOT_FOUND + id));
//
//		if (partialUpdate) {
//			if (employeeVo.getFirstName() != null) {
//				existingEmployee.setFirstName(employeeVo.getFirstName());
//			}
//			if (employeeVo.getLastName() != null) {
//				existingEmployee.setLastName(employeeVo.getLastName());
//			}
//			if (employeeVo.getEmail() != null) {
//				existingEmployee.setEmail(employeeVo.getEmail());
//			}
//			if (employeeVo.getSalary() != null) {
//				existingEmployee.setSalary(employeeVo.getSalary());
//			}
//
//		} else {
//
//			existingEmployee.setFirstName(employeeVo.getFirstName());
//			existingEmployee.setLastName(employeeVo.getLastName());
//			existingEmployee.setAge(employeeVo.getAge());
//			existingEmployee.setEmail(employeeVo.getEmail());
//			existingEmployee.setGender(employeeVo.getGender());
//			existingEmployee.setSalary(employeeVo.getSalary());
//			existingEmployee.setStatus(employeeVo.getStatus());
//			existingEmployee.setContactNumber(employeeVo.getContactNumber());
//			existingEmployee.setDateOfBirth(employeeVo.getDateOfBirth());
//			existingEmployee.setHireDate(employeeVo.getHireDate());
//			existingEmployee.setJobTitle(employeeVo.getJobTitle());
//
//		}
//
//		Employee updatedEmployee = repo.save(existingEmployee);
//		return mapper.toDto(updatedEmployee);
//	}
 
	@Override
	public EmployeeVo update(EmployeeVo employeeVo, Long id, boolean partialUpdate) {
	    Employee existingEmployee = repo.findById(id)
	                                    .orElseThrow(() -> new RuntimeException(EMPLOYEE_NOT_FOUND + id));

	    if (partialUpdate) {
	        updateFields(existingEmployee, employeeVo);
	    } else {
	        existingEmployee = mapper.toEntity(employeeVo);
	    }

// 	    if (employeeVo.getDepartment() != null && employeeVo.getDepartment().getDeptId() != null) {
//	        Department department = service.findById(employeeVo.getDepartment().getDeptId());
//	        existingEmployee.setDepartment(department);
//	    }

	    Employee savedEmployee = repo.save(existingEmployee);
	    return mapper.toDto(savedEmployee);
	}

	private void updateFields(Employee existingEmployee, EmployeeVo updatedEmployeeVo) {
	    if (updatedEmployeeVo.getFirstName() != null) {
	        existingEmployee.setFirstName(updatedEmployeeVo.getFirstName());
	    }
	    if (updatedEmployeeVo.getLastName() != null) {
	        existingEmployee.setLastName(updatedEmployeeVo.getLastName());
	    }
	    if (updatedEmployeeVo.getEmail() != null) {
	        existingEmployee.setEmail(updatedEmployeeVo.getEmail());
	    }
	    if (updatedEmployeeVo.getSalary() != null) {
	        existingEmployee.setSalary(updatedEmployeeVo.getSalary());
	    }
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
        Employee employee = mapper.toEntity(employeeVo);
        Employee savedEmployee = repo.save(employee);
        return mapper.toDto(savedEmployee);
    }
}