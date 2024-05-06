package net.texala.employee.web.rest.address;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import net.texala.employee.model.address.Address;
import net.texala.employee.service.address.AddressService;

@RestController
public class AddressController {
	@Autowired
	private AddressService addressService;

	@GetMapping("/api/address")
	public ResponseEntity<List<Address>> findAll() {

		List<Address> find = addressService.findAll();
		if (find.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
		}
		return ResponseEntity.ok(find);

	}

	@PostMapping("/api/address")
	public ResponseEntity<Address> save(@RequestBody Address address) {
		Address saved = addressService.save(address);
		if (saved != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@DeleteMapping("/api/address/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") int addressId) {
		try {
			boolean deleted = addressService.deleteById(addressId);
			return ResponseEntity.ok("Address " + addressId + "Deleted successfully");
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address with Id " + addressId + " not found");
		}
	}

	@PutMapping("/api/address/{id}")
	public ResponseEntity<String> update(@RequestBody Address address, @PathVariable("id") int addressId) {
		try {
			Address update = addressService.update(address, addressId);
			return ResponseEntity.ok("Address" + addressId + " updated successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address with Id" + addressId + "not found");
		}

	}
	@PatchMapping("/api/address/{id}")
	public ResponseEntity<String> updatePatch(@PathVariable("id") int addressId,@RequestBody Address address){
		try {
			Address updatedAddress = addressService.update(address, addressId);
			return ResponseEntity.ok().body("Address with ID " + addressId + " updated successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address with ID " + addressId + " not found");
		}
	}

}
