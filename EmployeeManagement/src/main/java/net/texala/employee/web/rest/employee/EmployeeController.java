package net.texala.employee.web.rest.employee;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<Employee> save(@RequestBody Employee employee) {
		Employee saved = employeeService.save(employee);
		if (saved != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@DeleteMapping("/api/employee/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") int id) {
		try {
			boolean deleted = employeeService.deleteById(id);
			return ResponseEntity.ok("Employee " + id + "Deleted successfully");
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with Id " + id + " not found");
		}
	}

	@PutMapping("/api/employee/{id}")
	public ResponseEntity<String> update(@RequestBody Employee employee, @PathVariable("id") int id) {
		try {
			Employee update = employeeService.update(employee, id);
			return ResponseEntity.ok("Employee" + id + " updated successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with Id" + id + "not found");
		}
	}
	
	@PatchMapping("/api/employee/{id}")
	public ResponseEntity<String> updatePatch(@PathVariable("id") int id,@RequestBody Employee employee){
		try {
			Employee updatedEmployee = employeeService.update(employee, id);
			return ResponseEntity.ok().body("Employee with ID " + id + " updated successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with ID " + id + " not found");
		}
	}
	@PostMapping("/api/activate/{id}")
    public ResponseEntity<String> activateRecord(@PathVariable("id") Integer id) {
        try {
            employeeService.activateRecord(id);
            return ResponseEntity.ok("Record activated successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();  
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Record is already active");
        }
    }
	@PostMapping("/api/deactivate/{id}")
    public ResponseEntity<String> deactivateRecord(@PathVariable("id") Integer id) {
        try {
            employeeService.deactivateRecord(id);
            return ResponseEntity.ok("Record deactivated successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();  
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Record is already deactive");
        }
    }
}
