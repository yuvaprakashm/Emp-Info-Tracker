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

import net.texala.employee.mapper.address.AddressMapper;
import net.texala.employee.model.address.Address;
import net.texala.employee.service.address.AddressService;
import net.texala.employee.vo.address.AddressVo;
import net.texala.employee.vo.employee.EmployeeVo;

@RestController
public class AddressController {
	@Autowired
	private AddressService addressService;
	@Autowired
	private AddressMapper addressMapper;

	@GetMapping("/api/address")
	public ResponseEntity<List<AddressVo>> findAll() {
		List<AddressVo> addressVo = addressService.findAll();
		return ResponseEntity.ok(addressVo);
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
		AddressVo deactivate = addressService.activateRecord(addressId);
		return ResponseEntity.ok(deactivate);
	}
}
