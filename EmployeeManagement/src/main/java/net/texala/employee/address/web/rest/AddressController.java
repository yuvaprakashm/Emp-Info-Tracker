package net.texala.employee.address.web.rest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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
import net.texala.employee.address.service.AddressService;
import net.texala.employee.address.vo.AddressVo;
import net.texala.employee.restresponse.RestResponse;
import net.texala.employee.reststatus.RestStatus;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

	private AddressService addressService;

	@GetMapping("/search")
	public ResponseEntity<RestResponse<Page<AddressVo>>> search(
			@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", required = false, defaultValue = "" + Integer.MAX_VALUE) Integer pageSize,
			@RequestParam(name = "sortBy", required = false, defaultValue = "createdDate:asc") String sortBy,
			@RequestParam(name = "filterBy", required = false, defaultValue = "") String filterBy,
			@RequestParam(name = "searchText", required = false) String searchText) {

		final RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record fetch Succesfully");
		final Page<AddressVo> search = addressService.search(pageNo, pageSize, sortBy, filterBy, searchText);
		final RestResponse<Page<AddressVo>> response = new RestResponse<>(search, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/findAll")
	public ResponseEntity<RestResponse<List<AddressVo>>> findAll() {

		RestStatus<List<AddressVo>> restStatus = new RestStatus<>(HttpStatus.OK, "Record fetch Succesfully");
		List<AddressVo> list = addressService.findAll();
		if (CollectionUtils.isEmpty(list))
			restStatus = new RestStatus<>(HttpStatus.OK, "No record Found");
		final RestResponse<List<AddressVo>> response = new RestResponse<>(list, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<RestResponse<AddressVo>> add(@RequestBody(required = true) AddressVo addressVo) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record add Succesfully");
		final RestResponse<AddressVo> response = new RestResponse<>(addressService.add(addressVo), restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RestResponse<AddressVo>> update(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) AddressVo addressVo) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record update Succesfully");
		addressVo.setId(id);
		final RestResponse<AddressVo> response = new RestResponse<>(addressService.update(addressVo), restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/activate/{id}")
	public ResponseEntity<RestResponse<Void>> activate(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record activate Succesfully");
		addressService.active(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/deactivate/{id}")
	public ResponseEntity<RestResponse<Void>> deactivate(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record deactivate Succesfully");
		addressService.deactive(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Void>> delete(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<>(HttpStatus.OK, "Record Deleted Succesfully");
		addressService.delete(id);
		final RestResponse<Void> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}