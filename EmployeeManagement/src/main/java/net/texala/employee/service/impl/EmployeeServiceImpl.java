package net.texala.employee.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import net.texala.employee.mapper.EmployeeMapper;
import net.texala.employee.model.Employee;
import net.texala.employee.repository.EmployeeRepository;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.vo.EmployeeVo;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public List<EmployeeVo> findAll() {
		List<Employee> employees = employeeRepository.findAll();
		if (employees.isEmpty()) {

			return Collections.emptyList();
		}
		return employees.stream().map(employeeMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public EmployeeVo save(EmployeeVo employeeVo) {
		Employee employee = employeeMapper.toEntity(employeeVo);
		employee = employeeRepository.save(employee);
		if (employee != null) {
			return employeeMapper.toVo(employee);
		} else {
			return null;
		}
	}

	@Override
	public String deleteById(int id) {
		try {
			employeeRepository.deleteById(id);
			return "Employee " + id + " deleted successfully";
		} catch (EmptyResultDataAccessException e) {
			return "Employee with ID " + id + " not found";
		}
	}

	@Override
	public EmployeeVo update(EmployeeVo employeeVo, int id) {
		try {
			Employee existingEmployee = employeeRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Employee with Id " + id + " not found"));
			existingEmployee.setFirstName(employeeVo.getFirstName());
			existingEmployee.setLastName(employeeVo.getLastName());
			existingEmployee.setAge(employeeVo.getAge());
			existingEmployee.setEmail(employeeVo.getEmail());
			existingEmployee.setGender(employeeVo.getGender().toString());
			existingEmployee.setSalary(employeeVo.getSalary());
			existingEmployee = employeeRepository.save(existingEmployee);
			return employeeMapper.toVo(existingEmployee);
		} catch (RuntimeException e) {
			throw new RuntimeException("Error updating employee with ID " + id + ": " + e.getMessage(), e);
		}
	}

	@Override
	public EmployeeVo updatePatch(EmployeeVo employeeVo, int id) {
		try {
			Employee existingEmployee = employeeRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Employee with Id " + id + " not found"));

			existingEmployee.setFirstName(employeeVo.getFirstName());
			existingEmployee.setLastName(employeeVo.getLastName());
			existingEmployee.setAge(employeeVo.getAge());
			existingEmployee.setEmail(employeeVo.getEmail());

			existingEmployee = employeeRepository.save(existingEmployee);
			return employeeMapper.toVo(existingEmployee);
		} catch (RuntimeException e) {
			throw new RuntimeException("Error updating employee with ID " + id + ": " + e.getMessage(), e);
		}
	}

	@Override
	public EmployeeVo activateRecord(Integer id) {
		try {
			Employee emp = employeeRepository.findById(id)
					.orElseThrow(() -> new NoSuchElementException("Employee with ID " + id + " not found"));
			if (emp.getActive() == null || !emp.getActive()) {
				emp.setActive(true);
				return employeeMapper.toVo(employeeRepository.save(emp));
			} else {
				throw new RuntimeException("Record is already active");
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Employee with ID " + id + " not found");
		} catch (RuntimeException e) {
			throw new RuntimeException("Error activating employee with ID " + id + ": " + e.getMessage(), e);
		}
	}

	@Override
	public EmployeeVo deactivateRecord(Integer id) {
		try {
			Employee emp = employeeRepository.findById(id)
					.orElseThrow(() -> new NoSuchElementException("Employee with ID " + id + " not found"));
			if (emp.getActive() != null && emp.getActive()) {
				emp.setActive(false);
				return employeeMapper.toVo(employeeRepository.save(emp));
			} else {
				throw new RuntimeException("Record is already deactive");
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Employee with ID " + id + " not found");
		} catch (RuntimeException e) {
			throw new RuntimeException("Error deactivating employee with ID " + id + ": " + e.getMessage(), e);
		}
	}
 
}