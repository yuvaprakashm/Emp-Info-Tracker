package net.texala.employee.service.impl.address;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import net.texala.employee.mapper.address.AddressMapper;
import net.texala.employee.model.address.Address;
import net.texala.employee.model.employee.Employee;
import net.texala.employee.repository.address.AddressRepository;
import net.texala.employee.service.address.AddressService;
import net.texala.employee.vo.address.AddressVo;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private AddressMapper addressMapper;

	@Override
	public List<AddressVo> findAll() {
		List<Address> address = addressRepository.findAll();
		if (address.isEmpty()) {

			return Collections.emptyList();
		}
		return address.stream().map(addressMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public AddressVo save(AddressVo addressVo) {
		Address address = addressMapper.toEntity(addressVo);
		address = addressRepository.save(address);
		if (address != null) {
			return addressMapper.toVo(address);
		} else {
			return null;
		}

	}

	@Override
	public String deleteById(int addressId) {
		try {
			addressRepository.deleteById(addressId);
			return "Address " + addressId + " deleted successfully";
		} catch (EmptyResultDataAccessException e) {
			return "Address with ID " + addressId + " not found";
		}
	}

	@Override
	public AddressVo update(AddressVo addressVo, int addressId) {
		try {
			Address existingAddress = addressRepository.findById(addressId)
					.orElseThrow(() -> new RuntimeException("Address with Id" + addressId + "not found"));
			existingAddress.setStreet(addressVo.getStreet());
			existingAddress.setCity(addressVo.getCity());
			existingAddress.setState(addressVo.getState());
			existingAddress.setZipcode(addressVo.getZipcode());
			existingAddress = addressRepository.save(existingAddress);
			return addressMapper.toVo(existingAddress);

		} catch (RuntimeException e) {
			throw new RuntimeException("Error updating employee with ID " + addressId + ": " + e.getMessage(), e);
		}
	}

	@Override
	public AddressVo updatePatch(AddressVo addressVo, int addressId) {
		try {
			Address existingAddress = addressRepository.findById(addressId)
					.orElseThrow(() -> new RuntimeException("Address with Id " + addressId + " not found"));
			existingAddress.setStreet(addressVo.getStreet());
			existingAddress.setCity(addressVo.getCity());
			existingAddress.setState(addressVo.getState());
			existingAddress = addressRepository.save(existingAddress);
			return addressMapper.toVo(existingAddress);
		} catch (RuntimeException e) {
			throw new RuntimeException("Error updating address with ID " + addressId + ": " + e.getMessage(), e);
		}
	}

	@Override
	public AddressVo activateRecord(Integer addressId) {
		try {
			Address add = addressRepository.findById(addressId)
					.orElseThrow(() -> new NoSuchElementException("Address with ID " + addressId + " not found"));
			if (add.getActive() == null || !add.getActive()) {
				add.setActive(true);
				return addressMapper.toVo(addressRepository.save(add));
			} else {
				throw new RuntimeException("Record is already active");
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Address with ID " + addressId + " not found");
		} catch (RuntimeException e) {
			throw new RuntimeException("Error activating employee with ID " + addressId + ": " + e.getMessage(), e);
		}
	}

	@Override
	public AddressVo deactivateRecord(Integer addressId) {
		try {
		Address add = addressRepository.findById(addressId)
				.orElseThrow(() -> new NoSuchElementException("Address with ID " + addressId + " not found"));
		if (add.getActive() != null && add.getActive()) {
			add.setActive(false);
			return addressMapper.toVo(addressRepository.save(add));
		} else {
			throw new RuntimeException("Record is already deactive");
		} }catch (NoSuchElementException e) {
			throw new NoSuchElementException("Employee with ID " + addressId + " not found");
		} catch (RuntimeException e) {
			throw new RuntimeException("Error deactivating employee with ID " + addressId + ": " + e.getMessage(), e);
		}

}
}
