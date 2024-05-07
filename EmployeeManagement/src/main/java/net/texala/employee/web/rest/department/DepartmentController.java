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

import net.texala.department.mapper.DepartmentMapper;
import net.texala.employee.model.department.Department;
import net.texala.employee.service.department.DepartmentService;
import net.texala.employee.service.employee.EmployeeService;
import net.texala.employee.vo.department.DepartmentVo;
import net.texala.employee.vo.employee.EmployeeVo;

@RestController
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private DepartmentMapper departmentMapper;

	@GetMapping("/api/department")
	public ResponseEntity<List<DepartmentVo>> findAll() {
		List<DepartmentVo> employeeVo = departmentService.findAll();
		return ResponseEntity.ok(employeeVo);
	}

	@PostMapping("/api/department")
	public ResponseEntity<DepartmentVo> save(@RequestBody DepartmentVo departmentVo) {
		DepartmentVo savedVo = departmentService.save(departmentVo);
		return ResponseEntity.ok(savedVo);

	}

	@DeleteMapping("/api/department/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") int deptId) {
		String deleteVo = departmentService.deleteById(deptId);
		return ResponseEntity.ok(deleteVo);
	}

	@PutMapping("/api/department/{id}")
	public ResponseEntity<DepartmentVo> update(@RequestBody DepartmentVo departmentVo, @PathVariable("id") int deptId) {
		Department department = departmentMapper.toEntity(departmentVo);
		DepartmentVo updatedVo = departmentService.update(departmentVo, deptId);
		return ResponseEntity.ok(updatedVo);
	}

	@PatchMapping("/api/department/{id}")
	public ResponseEntity<DepartmentVo> updatePatch(@PathVariable("id") int deptId,
			@RequestBody DepartmentVo departmentVo) {
		Department department = departmentMapper.toEntity(departmentVo);
		DepartmentVo patchVo = departmentService.updatePatch(departmentVo, deptId);
		return ResponseEntity.ok(patchVo);
	}

	@PostMapping("/dept/activate/{id}")
	public ResponseEntity<DepartmentVo> activateRecord(@PathVariable("id") Integer deptId) {
		DepartmentVo activate = departmentService.activateRecord(deptId);
		return ResponseEntity.ok(activate);
	}

	@PostMapping("/dept/deactivate/{id}")
	public ResponseEntity<DepartmentVo> deactivateRecord(@PathVariable("id") Integer deptId) {

		DepartmentVo deactivate = departmentService.deactivateRecord(deptId);
		return ResponseEntity.ok(deactivate);

	}
}