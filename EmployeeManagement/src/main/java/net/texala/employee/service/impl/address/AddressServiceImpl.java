package net.texala.employee.service.impl.address;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.texala.employee.model.address.Address;
import net.texala.employee.repository.address.AddressRepository;
import net.texala.employee.service.address.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;

	@Override
	public List<Address> findAll() {

		return (List<Address>) addressRepository.findAll();
	}

	@Override
	public Address save(Address address) {

		return addressRepository.save(address);
	}

	@Override
	public boolean deleteById(int addressId) {
		addressRepository.deleteById(addressId);
		return false;
	}

	@Override
	public Address update(Address address, int addressId) {
		Address existingAddress = addressRepository.findById(addressId)
				.orElseThrow(() -> new RuntimeException("Address with Id" + addressId + "not found"));
		existingAddress.setStreet(address.getStreet());
		existingAddress.setCity(address.getCity());
		existingAddress.setState(address.getState());
		existingAddress.setZipcode(address.getZipcode());

		return addressRepository.save(existingAddress);

	}

	@Override
	public Address updatePatch(Address address, int addressId) {
		Address existingAddress = addressRepository.findById(addressId)
				.orElseThrow(() -> new RuntimeException("Address with Id " + addressId + " not found"));
		if (address.getStreet() != null) {
			existingAddress.setStreet(address.getStreet());
		}
		if (address.getCity() != null) {
			existingAddress.setCity(address.getCity());
		}
		if (address.getState() != null) {
			existingAddress.setState(address.getState());
		}
		return addressRepository.save(existingAddress);
	}

	@Override
	public Address activateRecord(Integer addressId) {
		Address add = addressRepository.findById(addressId)
				.orElseThrow(() -> new NoSuchElementException("Address with ID " + addressId + " not found"));
		if (add.getActive() == null || !add.getActive()) {
			add.setActive(true);
			return addressRepository.save(add);
		} else {
			throw new RuntimeException("Record is already active");
		}
	}

	@Override
	public Address deactivateRecord(Integer addressId) {
		Address add = addressRepository.findById(addressId)
				.orElseThrow(() -> new NoSuchElementException("Address with ID " + addressId + " not found"));
		if (add.getActive() != null && add.getActive()) {
			add.setActive(false);
			return addressRepository.save(add);
		} else {
			throw new RuntimeException("Record is already deactive");
		}

	}
}