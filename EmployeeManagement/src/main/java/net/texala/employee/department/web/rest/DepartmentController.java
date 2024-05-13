package net.texala.employee.department.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
			@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", required = false, defaultValue = "" + Integer.MAX_VALUE) Integer pageSize,
			@RequestParam(name = "sortBy", required = false, defaultValue = "createdDate:asc") String sortBy,
			@RequestParam(name = "filterBy", required = false, defaultValue = "") String filterBy,
			@RequestParam(name = "searchText", required = false) String searchText) {

		final RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record fetch Succesfully");
		final Page<DepartmentVo> search = departmentService.search(pageNo, pageSize, sortBy, filterBy, searchText);
		final RestResponse<Page<DepartmentVo>> response = new RestResponse<>(search, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("/api/department")
	public ResponseEntity<List<DepartmentVo>> findAll() {
		List<DepartmentVo> departmentVo = departmentService.findAll();
		return ResponseEntity.ok(departmentVo);
	}

	@PostMapping("/add")
	public ResponseEntity<RestResponse<DepartmentVo>> add(@RequestBody(required = true) DepartmentVo departmentVo) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record add Succesfully");
		final RestResponse<DepartmentVo> response = new RestResponse<>(departmentService.add(departmentVo), restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> delete(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record Deleted Succesfully");
		departmentService.delete(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RestResponse<DepartmentVo>> update(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) DepartmentVo departmentVo) {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record update Succesfully");
		departmentVo.setDeptId(id);
		final RestResponse<DepartmentVo> response = new RestResponse<>(departmentService.update(departmentVo, id, false),
				restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@PatchMapping("/{id}")
	public ResponseEntity<RestResponse<DepartmentVo>> updatePatch(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) DepartmentVo departmentVo) {
		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record update Succesfully");
		departmentVo.setDeptId(id);
		final RestResponse<DepartmentVo> response = new RestResponse<>(departmentService.update(departmentVo, id, true),
				restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/activate/{id}")
	public ResponseEntity<RestResponse<Void>> activate(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record activate Succesfully");
		departmentService.active(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/deactivate/{id}")
	public ResponseEntity<RestResponse<Void>> deactivate(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record deactivate Succesfully");
		departmentService.deactive(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}