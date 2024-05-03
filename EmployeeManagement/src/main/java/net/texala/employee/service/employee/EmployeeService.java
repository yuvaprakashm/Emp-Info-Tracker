package net.texala.employee.service.employee;

import java.util.List;

import net.texala.employee.model.employee.Employee;
 
public interface EmployeeService {
	List<Employee> findAll();

	Employee save(Employee employee);
}
