package net.texala.employee.service.impl.employee;

import java.util.List;
import java.util.NoSuchElementException;
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

	@Override
	public boolean deleteById(int id) {
		employeeRepository.deleteById(id);
		return false;
	}

	@Override
	public Employee update(Employee employee, int id) {
		Employee existingEmployee = employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee with Id" + id + "not found"));
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setAge(employee.getAge());
		existingEmployee.setEmail(employee.getEmail());
		existingEmployee.setGender(employee.getGender());
		existingEmployee.setSalary(employee.getSalary());
		return employeeRepository.save(existingEmployee);
	}

	@Override
	public Employee updatePatch(Employee employee, int id) {
		Employee existingEmployee = employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee with Id " + id + " not found"));

		if (employee.getFirstName() != null) {
			existingEmployee.setFirstName(employee.getFirstName());
		}
		if (employee.getLastName() != null) {
			existingEmployee.setLastName(employee.getLastName());
		}
		if (employee.getAge() > 0) {
			existingEmployee.setAge(employee.getAge());
		}
		if (employee.getEmail() != null) {
			existingEmployee.setEmail(employee.getEmail());
		}

		return employeeRepository.save(existingEmployee);
	}

	@Override
	public Employee activateRecord(Integer id) {
		Employee emp = employeeRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID " + id + " not found"));
		if (emp.getActive() == null || !emp.getActive()) {
			emp.setActive(true);
			return employeeRepository.save(emp);
		} else {
			throw new RuntimeException("Record is already active");
		}
	}

	@Override
	public Employee deactivateRecord(Integer id) {
		Employee emp = employeeRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID " + id + " not found"));
		if (emp.getActive() != null && emp.getActive()) {
			emp.setActive(false);
			return employeeRepository.save(emp);
		} else {
			throw new RuntimeException("Record is already deactive");
		}
	}

}
