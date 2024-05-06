package net.texala.employee.web.rest.department;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
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
import net.texala.employee.model.department.Department;
import net.texala.employee.service.department.DepartmentService;
import net.texala.employee.service.employee.EmployeeService;

@RestController
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;

	@GetMapping("/api/department")
	public ResponseEntity<List<Department>> findAll() {
		List<Department> find = departmentService.findAll();
		if (find.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
		}
		return ResponseEntity.ok(find);

	}

	@PostMapping("/api/department")
	public ResponseEntity<Department> save(@RequestBody Department department) {
		Department saved = departmentService.save(department);
		if (saved != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@DeleteMapping("/api/department/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") int deptId) {
		try {
			boolean deleted = departmentService.deleteById(deptId);
			return ResponseEntity.ok("Department " + deptId + "Deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department with Id " + deptId + " not found");
		}
	}

	@PutMapping("/api/department/{id}")
	public ResponseEntity<String> update(@RequestBody Department department, @PathVariable("id") int deptId) {
		try {
			Department update = departmentService.update(department, deptId);
			return ResponseEntity.ok("Department" + deptId + " updated successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department with Id" + deptId + "not found");
		}
	}

	@PatchMapping("/api/department/{id}")
	public ResponseEntity<String> updatePatch(@PathVariable("id") int deptId, @RequestBody Department department) {
		try {
			Department updatedDepartment = departmentService.update(department, deptId);
			return ResponseEntity.ok().body("Department with ID " + deptId + " updated successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department with ID " + deptId + " not found");
		}
	}

	@PostMapping("/dept/activate/{id}")
	public ResponseEntity<String> activateRecord(@PathVariable("id") Integer deptId) {
		try {
			departmentService.activateRecord(deptId);
			return ResponseEntity.ok("Record activated successfully");
		} catch (NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body("Record is already active");
		}
	}

	@PostMapping("/dept/deactivate/{id}")
	public ResponseEntity<String> deactivateRecord(@PathVariable("id") Integer deptId) {
		try {
			departmentService.deactivateRecord(deptId);
			return ResponseEntity.ok("Record deactivated successfully");
		} catch (NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body("Record is already deactive");
		}
	}
}
