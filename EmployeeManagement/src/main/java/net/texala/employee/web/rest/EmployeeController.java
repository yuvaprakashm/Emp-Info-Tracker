package net.texala.employee.web.rest;

import java.util.List;

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
import net.texala.employee.restresponse.RestResponse;
import net.texala.employee.reststatus.RestStatus;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.vo.EmployeeVo;
 

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	 

	@GetMapping("/api/employee")
	public ResponseEntity<List<EmployeeVo>> findAll() {
		List<EmployeeVo> employeeVo = employeeService.findAll();
		return ResponseEntity.ok(employeeVo);
	}

	@PostMapping("/add")
	public ResponseEntity<RestResponse<EmployeeVo>> add(@RequestBody(required = true) EmployeeVo employeeVo) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record add Succesfully");
		final RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.add(employeeVo), restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> delete(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record Deleted Succesfully");
		employeeService.delete(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RestResponse<EmployeeVo>> update(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) EmployeeVo employeeVo) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record update Succesfully");
		employeeVo.setId(id);
		final RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.update(employeeVo, id), restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
 
	@PatchMapping("/activate/{id}")
	public ResponseEntity<RestResponse<Void>> activate(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record activate Succesfully");
		employeeService.active(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/deactivate/{id}")
	public ResponseEntity<RestResponse<Void>> deactivate(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record deactivate Succesfully");
		employeeService.deactive(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
