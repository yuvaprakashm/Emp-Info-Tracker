package net.texala.employee.service.impl.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.texala.employee.model.employee.Employee;
import net.texala.employee.repository.employee.EmployeeRepository;
import net.texala.employee.service.employee.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> findAll() {

		return (List<Employee>) employeeRepository.findAll();
	}

	@Override
	public Employee save(Employee employee) {

		return employeeRepository.save(employee);
	}

}
