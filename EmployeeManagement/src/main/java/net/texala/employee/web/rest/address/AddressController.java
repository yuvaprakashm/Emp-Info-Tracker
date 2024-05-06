package net.texala.employee.web.rest.address;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
}
