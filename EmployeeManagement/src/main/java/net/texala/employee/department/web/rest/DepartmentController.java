package net.texala.employee.department.web.rest;

import static net.texala.employee.constants.Constants.*;
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
import lombok.RequiredArgsConstructor;
import net.texala.employee.department.model.Department;
import net.texala.employee.department.service.DepartmentService;
import net.texala.employee.department.vo.DepartmentVo;
import net.texala.employee.restresponse.RestResponse;
import net.texala.employee.reststatus.RestStatus;

@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;

	@GetMapping("/search")
	public ResponseEntity<RestResponse<Page<DepartmentVo>>> search(
			@RequestParam(name = PAGE_NO, required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(name = PAGE_SIZE, required = false, defaultValue = "" + Integer.MAX_VALUE) Integer pageSize,
			@RequestParam(name = SORT_BY, required = false, defaultValue = "createdDate:asc") String sortBy,
			@RequestParam(name = FILTER_BY, required = false, defaultValue = "") String filterBy,
			@RequestParam(name = SEARCH_TEXT, required = false) String searchText) {

		final RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_FETCH_SUCCESS_MESSAGE);
		final Page<DepartmentVo> search = departmentService.search(pageNo, pageSize, sortBy, filterBy, searchText);
		final RestResponse<Page<DepartmentVo>> response = new RestResponse<>(search, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/records")
	public ResponseEntity<RestResponse<List<DepartmentVo>>> findAll() {
	    List<DepartmentVo> list = departmentService.findAll();
	    RestStatus<List<DepartmentVo>> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_FETCH_SUCCESS_MESSAGE);
	    RestResponse<List<DepartmentVo>> response = new RestResponse<>(list, restStatus);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@GetMapping("/records/{id}")
	public ResponseEntity<RestResponse<DepartmentVo>> findById(@PathVariable(name = "id", required = true) Long id) {
		RestStatus<DepartmentVo> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_FETCH_SUCCESS_MESSAGE);
		DepartmentVo departmentVo = departmentService.findById(id);
		final RestResponse<DepartmentVo> response = new RestResponse<>(departmentVo, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
 
	@PostMapping("/records")
	public ResponseEntity<RestResponse<DepartmentVo>> add(@RequestBody(required = true) DepartmentVo departmentVo) {
	    RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_ADD_SUCCESS_MESSAGE);
	    final RestResponse<DepartmentVo> response = new RestResponse<>(departmentService.add(departmentVo), restStatus);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}


	@DeleteMapping("/records/{id}")
	public ResponseEntity<RestResponse<Void>> delete(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_DELETED_SUCCESS_MESSAGE);
		departmentService.delete(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/records/{id}")
	public ResponseEntity<RestResponse<DepartmentVo>> update(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) DepartmentVo departmentVo) {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_UPDATE_SUCCESS_MESSAGE);
		departmentVo.setDeptId(id);
		final RestResponse<DepartmentVo> response = new RestResponse<>(
				departmentService.update(departmentVo, id, false), restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/records/{id}")
	public ResponseEntity<RestResponse<DepartmentVo>> updatePatch(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) DepartmentVo departmentVo) {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_UPDATE_SUCCESS_MESSAGE);
		departmentVo.setDeptId(id);
		final RestResponse<DepartmentVo> response = new RestResponse<>(departmentService.update(departmentVo, id, true),
				restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/records/{id}/activate")
	public ResponseEntity<RestResponse<Void>> activate(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_ACTIVE_SUCCESS_MESSAGE);
		departmentService.active(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/records/{id}/deactivate")
	public ResponseEntity<RestResponse<Void>> deactivate(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, RECORD_DEACTIVE_SUCCESS_MESSAGE);
		departmentService.deactive(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	@GetMapping("/download")
//	public ResponseEntity<RestResponse<Resource>>downloadCsv() {
//		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record downloaded Succesfully");
//		String csvContent = departmentService.generateCsvContent();
//		ByteArrayResource resource = new ByteArrayResource(csvContent.getBytes());
//		HttpHeaders headers = new HttpHeaders();
//		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
//		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"department-data.csv\"");
//		return new ResponseEntity<>(response, HttpStatus.OK).headers(headers).contentType(MediaType.parseMediaType("text/csv")).body(resource);
//	}

	@GetMapping("/download")
	public ResponseEntity<ByteArrayResource> downloadCsv() {
		String csvContent = departmentService.generateCsvContent();
		ByteArrayResource resource = new ByteArrayResource(csvContent.getBytes());
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"department-data.csv\"");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("text/csv")).body(resource);
	}
}