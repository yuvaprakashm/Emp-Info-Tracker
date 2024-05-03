package net.texala.employee.web.rest.employee;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.texala.employee.model.employee.Employee;
import net.texala.employee.service.employee.EmployeeService;
@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/api/employee")
	public ResponseEntity<List<Employee>> findAll() {
		List<Employee> find = employeeService.findAll();
		if (find.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
		}
		return ResponseEntity.ok(find);

	}
	
	@PostMapping("/api/employee")
	public ResponseEntity<Employee> save(@RequestBody Employee employee){
		Employee saved = employeeService.save(employee);
		if (saved != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
