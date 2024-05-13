package net.texala.employee.service.impl;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import net.texala.employee.Specification.CommonSpecification;
import net.texala.employee.Util.Utility;
import net.texala.employee.enums.Gender;
import net.texala.employee.enums.GenericStatus;
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
	
	 private static final String FILE_PATH = "D:\\Emp_Export";
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
		return repo.findById(id).orElseThrow(() -> new RuntimeException("Id not Found"));
	}

	@Override
	public EmployeeVo add(EmployeeVo employeeVo) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeVo, employee);
		return mapper.toDto(repo.save(employee));
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
		Employee existingEmployee = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

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

		}

		Employee updatedEmployee = repo.save(existingEmployee);
		return mapper.toDto(updatedEmployee);
	}

	@Override
	public void save(String filename) throws IOException, CsvException {
	    try (CSVReader reader = new CSVReader(new FileReader(FILE_PATH + filename))) {
	        List<String[]> rows = reader.readAll();
	        List<Employee> employeeList = new ArrayList<>();

	        for (String[] row : rows) {
	            Employee employee = new Employee();
	            // Assuming the CSV file contains columns in the order: id, firstName, lastName, age, email, gender, salary, status
	            employee.setId(Long.parseLong(row[0]));
	            employee.setFirstName(row[1]);
	            employee.setLastName(row[2]);
	            employee.setAge(Integer.parseInt(row[3]));
	            employee.setEmail(row[4]);
	            employee.setGender(Gender.valueOf(row[5])); // Assuming Gender is an enum
	            employee.setSalary(Integer.parseInt(row[6]));
	            employee.setStatus(GenericStatus.valueOf(row[7])); // Assuming GenericStatus is an enum

	            employeeList.add(employee);
	        }
	        repo.saveAll(employeeList);
	    } catch (IOException | CsvException e) {
	        // Handle IOException or CsvException
	        e.printStackTrace();
	        throw e;
	    }
	}

	@Override
	public List<EmployeeVo> findAllFiles() {
	    return findAll();
	}


	 
}