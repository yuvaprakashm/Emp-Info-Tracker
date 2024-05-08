package net.texala.employee.address.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.texala.employee.address.mapper.AddressMapper;
import net.texala.employee.address.model.Address;
import net.texala.employee.address.service.AddressService;
import net.texala.employee.address.vo.AddressVo;

@RestController
public class AddressController {
	private static final Logger logger = LogManager.getLogger(AddressController.class);

	@Autowired
	private AddressService addressService;
	@Autowired
	private AddressMapper addressMapper;

	@GetMapping("/api/address")
	public ResponseEntity<List<AddressVo>> findAll() {
		List<AddressVo> addressVo = addressService.findAll();
		return ResponseEntity.ok(addressVo);
	}

	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>> searchRecords(
	        @RequestParam(defaultValue = "0") int pageNo,
	        @RequestParam(defaultValue = "10000") int pageSize,
	        @RequestParam(defaultValue = "createdDate:desc") String sortBy,
	        @RequestParam(required = false) String filterBy,
	        @RequestParam(required = false) String searchText) {

	    try {
	        Page<AddressVo> page;
	        if (searchText != null && !searchText.isEmpty()) {
	            // Perform the search operation using searchText
	            page = addressService.searchRecords(pageNo, pageSize, sortBy, filterBy, searchText);
	        } else {
	            // Perform the search operation using pageNo, pageSize, sortBy, and filterBy
	            page = addressService.searchRecords(pageNo, pageSize, sortBy, filterBy, searchText);
	        }

	        // Prepare the response body
	        Map<String, Object> responseBody = new HashMap<>();
	        responseBody.put("data", page.getContent());
	        responseBody.put("status", "Success");

	        // Return the response with HTTP status 200 OK
	        return ResponseEntity.ok(responseBody);
	    } catch (Exception e) {
	        // Log the error
	        logger.error("Error occurred while searching records: " + e.getMessage(), e);

	        // Prepare error response
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("status", "Error");
	        errorResponse.put("message", "An error occurred while processing your request. Please try again later.");

	        // Return the error response with HTTP status 500 Internal Server Error
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}


	@PostMapping("/api/address")
	public ResponseEntity<AddressVo> save(@RequestBody AddressVo addressVo) {
		AddressVo savedVo = addressService.save(addressVo);
		return ResponseEntity.ok(savedVo);
	}

	@DeleteMapping("/api/address/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") int addressId) {
		String deleteVo = addressService.deleteById(addressId);
		return ResponseEntity.ok(deleteVo);
	}

	@PutMapping("/api/address/{id}")
	public ResponseEntity<AddressVo> update(@RequestBody AddressVo addressVo, @PathVariable("id") int addressId) {
		Address address = addressMapper.toEntity(addressVo);
		AddressVo addresVo = addressService.update(addressVo, addressId);
		return ResponseEntity.ok(addresVo);

	}

	@PatchMapping("/api/address/{id}")
	public ResponseEntity<AddressVo> updatePatch(@PathVariable("id") int addressId, @RequestBody AddressVo addressVo) {
		AddressVo patchVo = addressService.updatePatch(addressVo, addressId);
		return ResponseEntity.ok(patchVo);
	}

	@PostMapping("/add/activate/{id}")
	public ResponseEntity<AddressVo> activateRecord(@PathVariable("id") Integer addressId) {
		AddressVo activate = addressService.activateRecord(addressId);
		return ResponseEntity.ok(activate);
	}

	@PostMapping("/add/deactivate/{id}")
	public ResponseEntity<AddressVo> deactivateRecord(@PathVariable("id") Integer addressId) {
		AddressVo deactivate = addressService.deactivateRecord(addressId);
		return ResponseEntity.ok(deactivate);
	}
}
