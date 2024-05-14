package net.texala.employee.web.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.texala.employee.model.Employee;
import net.texala.employee.restresponse.RestResponse;
import net.texala.employee.reststatus.RestStatus;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.vo.EmployeeVo;
@RequestMapping("emp")
@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/search")
	public ResponseEntity<RestResponse<Page<EmployeeVo>>> search(
			@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", required = false, defaultValue = "" + Integer.MAX_VALUE) Integer pageSize,
			@RequestParam(name = "sortBy", required = false, defaultValue = "createdDate:asc") String sortBy,
			@RequestParam(name = "filterBy", required = false, defaultValue = "") String filterBy,
			@RequestParam(name = "searchText", required = false) String searchText) {

		final RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record fetch Succesfully");
		final Page<EmployeeVo> search = employeeService.search(pageNo, pageSize, sortBy, filterBy, searchText);
		final RestResponse<Page<EmployeeVo>> response = new RestResponse<>(search, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/records")
	public ResponseEntity<List<EmployeeVo>> findAll() {
		List<EmployeeVo> employeeVo = employeeService.findAll();
		return ResponseEntity.ok(employeeVo);
	}

	@GetMapping("/records/{id}")
	public ResponseEntity<Employee> findById(@PathVariable(name = "id", required = true) Long id) {
		Employee employee = employeeService.findById(id);
		return ResponseEntity.ok(employee);
	}

	@PostMapping("/records")
	public ResponseEntity<RestResponse<EmployeeVo>> add(@RequestBody(required = true) EmployeeVo employeeVo) {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record add Succesfully");
		final RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.add(employeeVo), restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/records/{id}")
	public ResponseEntity<RestResponse<Void>> delete(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record Deleted Succesfully");
		employeeService.delete(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/records/{id}")
	public ResponseEntity<RestResponse<EmployeeVo>> update(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) EmployeeVo employeeVo) {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record updated successfully");
		employeeVo.setId(id);
		final RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.update(employeeVo, id, false),
				restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/records/{id}")
	public ResponseEntity<RestResponse<EmployeeVo>> updatePatch(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) EmployeeVo employeeVo) {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record updated successfully");
		final RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.update(employeeVo, id, true),
				restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/records/{id}/activate")
	public ResponseEntity<RestResponse<Void>> activate(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record activate Succesfully");
		employeeService.active(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/records/{id}/deactivate")
	public ResponseEntity<RestResponse<Void>> deactivate(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record deactivate Succesfully");
		employeeService.deactive(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/download")
	public ResponseEntity<ByteArrayResource> downloadCsv() {
	    String csvContent = employeeService.generateCsvContent();
	    ByteArrayResource resource = new ByteArrayResource(csvContent.getBytes());
	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"employee-data.csv\"");
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentType(MediaType.parseMediaType("text/csv"))
	            .body(resource);
	}
}
