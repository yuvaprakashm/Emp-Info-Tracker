package net.texala.employee.web.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import net.texala.employee.mapper.EmployeeMapper;
import net.texala.employee.model.Employee;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.vo.EmployeeVo;
 

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployeeMapper employeeMapper;

	@GetMapping("/api/employee")
	public ResponseEntity<List<EmployeeVo>> findAll() {
		List<EmployeeVo> employeeVo = employeeService.findAll();
		return ResponseEntity.ok(employeeVo);
	}

	@PostMapping("/api/employee")
	public ResponseEntity<EmployeeVo> save(@RequestBody EmployeeVo employeeVo) {
		EmployeeVo savedVo = employeeService.save(employeeVo);
		return ResponseEntity.ok(savedVo);

	}

	@DeleteMapping("/api/employee/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") int id) {
		String deleteVo = employeeService.deleteById(id);
		return ResponseEntity.ok(deleteVo);
	}

	@PutMapping("/api/employee/{id}")
	public ResponseEntity<EmployeeVo> update(@RequestBody EmployeeVo employeeVo, @PathVariable("id") int id) {
		Employee employee = employeeMapper.toEntity(employeeVo);
		EmployeeVo updatedVo = employeeService.update(employeeVo, id);
		return ResponseEntity.ok(updatedVo);
	}

	@PatchMapping("/api/employee/{id}")
	public ResponseEntity<EmployeeVo> updatePatch(@PathVariable("id") int id, @RequestBody EmployeeVo employeeVo) {
		EmployeeVo patchVo = employeeService.updatePatch(employeeVo, id);
		return ResponseEntity.ok(patchVo);
	}

	@PostMapping("/api/activate/{id}")
	public ResponseEntity<EmployeeVo> activateRecord(@PathVariable("id") Integer id) {
		EmployeeVo activate = employeeService.activateRecord(id);
		return ResponseEntity.ok(activate);
	}

	@PostMapping("/api/deactivate/{id}")
	public ResponseEntity<EmployeeVo> deactivateRecord(@PathVariable("id") Integer id) {
		EmployeeVo deactivate = employeeService.deactivateRecord(id);
		return ResponseEntity.ok(deactivate);
	}

}
