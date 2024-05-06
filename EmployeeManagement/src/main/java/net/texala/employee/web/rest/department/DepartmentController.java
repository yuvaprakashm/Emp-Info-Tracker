package net.texala.employee.web.rest.department;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import net.texala.employee.model.department.Department;
import net.texala.employee.service.department.DepartmentService;

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
}
