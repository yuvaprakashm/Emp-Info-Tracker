package net.texala.employee.web.rest;

import static net.texala.employee.constants.Constants.*;
import javax.validation.Valid;
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
import lombok.AllArgsConstructor;
import net.texala.employee.common.RestResponse;
import net.texala.employee.common.RestStatus;
import net.texala.employee.enums.GenericStatus;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.vo.EmployeeVo;

@RequestMapping("/emp")
@RestController
@AllArgsConstructor
public class EmployeeController {

	@Autowired
	private final EmployeeService employeeService;

	@GetMapping("/search")
	public ResponseEntity<RestResponse<Page<EmployeeVo>>> search(
			@RequestParam(name = PAGE_NO, required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(name = PAGE_SIZE, required = false, defaultValue = "" + Integer.MAX_VALUE) Integer pageSize,
			@RequestParam(name = SORT_BY, required = false, defaultValue = "createdDate:asc") String sortBy,
			@RequestParam(name = FILTER_BY, required = false, defaultValue = "") String filterBy,
			@RequestParam(name = SEARCH_TEXT, required = false) String searchText) {
		final RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_FETCH_SUCCESS_MESSAGE);
		final Page<EmployeeVo> search = employeeService.search(pageNo, pageSize, sortBy, filterBy, searchText);
		final RestResponse<Page<EmployeeVo>> response = new RestResponse<>(search, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<EmployeeVo>> findById(@PathVariable(name = "id", required = true) Long id) {
		RestStatus<EmployeeVo> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_FETCH_SUCCESS_MESSAGE);
		final RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.findEmployeeVoById(id),
				restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<RestResponse<EmployeeVo>> add(@RequestBody(required = true) @Valid EmployeeVo employeeVo) {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_ADD_SUCCESS_MESSAGE);
		EmployeeVo add = employeeService.add(employeeVo);
		final RestResponse<EmployeeVo> response = new RestResponse<>(add, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RestResponse<EmployeeVo>> update(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) @Valid EmployeeVo employeeVo) {
		employeeVo.setId(id);
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_UPDATE_SUCCESS_MESSAGE);
		final RestResponse<EmployeeVo> response = new RestResponse<>(employeeService.update(employeeVo, id),
				restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> delete(@PathVariable(name = "id", required = true) Long id) {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_DELETED_SUCCESS_MESSAGE);
		employeeService.delete(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/{id}/status")
	public ResponseEntity<RestResponse<Void>> updateGenericStatus(@PathVariable(name = "id", required = true) Long id,
			@RequestParam(name = "status", required = true) GenericStatus status) {
		employeeService.updateGenericStatus(status, id);
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_STATUS_UPDATE_SUCCESS);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/download")
	public ResponseEntity<ByteArrayResource> downloadCsv() {
		String csvContent = employeeService.generateCsvContent();
		ByteArrayResource resource = new ByteArrayResource(csvContent.getBytes());
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"employee-data.csv\"");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("text/csv")).body(resource);
	}
}