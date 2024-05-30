package net.texala.employee.service.impl;

import static net.texala.employee.constants.Constants.*;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.texala.employee.address.mapper.AddressMapper;
import net.texala.employee.address.model.Address;
import net.texala.employee.address.vo.AddressVo;
import net.texala.employee.common.CommonSpecification;
import net.texala.employee.common.Utility;
import net.texala.employee.department.service.DepartmentService;
import net.texala.employee.enums.GenericStatus;
import net.texala.employee.exception.Exception.ServiceException;
import net.texala.employee.mapper.EmployeeMapper;
import net.texala.employee.model.Employee;
import net.texala.employee.repository.EmployeeRepository;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.vo.EmployeeVo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private EmployeeMapper employeeMapper;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private AddressMapper addressMapper;

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
	public Employee findById(Long id) {
		return employeeRepo.findById(id).orElseThrow(() -> new ServiceException(EMPLOYEE_NOT_FOUND + id));
	}
 
	@Override
	@Transactional
	public EmployeeVo add(EmployeeVo employeeVo) {
		try {
 			Employee employee = employeeMapper.toEntity(employeeVo);
			employee.setDepartment(departmentService.findById(employeeVo.getDeptId()));
			List<Address> addresses = new ArrayList<>();
			for (AddressVo addressVo : employeeVo.getAddresses()) {
				Address address = addressMapper.toEntity(addressVo);
	            address.setEmployee(employee);
	            addresses.add(address);	 
			}
			employee.setAddresses(addresses);
			return employeeMapper.toDto(employeeRepo.save(employee));
		} catch (Exception e) {
			throw new ServiceException(FAILED_ADD_EMP + e.getMessage());
		}
	}     

	@Transactional
	@Override
	public EmployeeVo update(EmployeeVo employeeVo, Long id) {
		findById(id);
		employeeVo.setId(id);
		Employee emp = employeeMapper.toEntity(employeeVo);
		emp.setDepartment(departmentService.findById(employeeVo.getDeptId()));
		List<Address> addresses = new ArrayList<>();
		for (AddressVo addressVo : employeeVo.getAddresses()) {
			Address address = addressMapper.toEntity(addressVo);
            address.setEmployee(emp);
            addresses.add(address);	 
		}
		emp.setAddresses(addresses);
		return employeeMapper.toDto(employeeRepo.save(emp));
	}

	@Transactional
	@Override
	public void delete(Long id) {
		findById(id);
		employeeRepo.updateStatus(GenericStatus.DELETED, id);
	}
	
	@Transactional
	@Override
	public void updateGenericStatus(GenericStatus status,Long id) {
		findById(id);
	 employeeRepo.updateStatus(status, id);
	}

	@Override
	public String generateCsvContent() {
		StringWriter writer = new StringWriter();
		try (@SuppressWarnings("deprecation")
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(EMPLOYEE_HEADER))) {
			Page<EmployeeVo> employeeList = search(0, Integer.MAX_VALUE, "createdDate:asc", Strings.EMPTY,
					Strings.EMPTY);
			for (EmployeeVo employee : employeeList.getContent()) {
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

	@Override
	public EmployeeVo findEmployeeVoById(Long id) {
		return employeeMapper.toDto(findById(id));
	}
}
