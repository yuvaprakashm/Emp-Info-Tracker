package net.texala.employee.service.employee;

import java.util.List;

import net.texala.employee.model.employee.Employee;
 
public interface EmployeeService {
	List<Employee> findAll();

	Employee save(Employee employee);
	
	boolean deleteById(int id);
	
	Employee update(Employee employee,int id);

	Employee updatePatch(Employee employee, int id);
}
