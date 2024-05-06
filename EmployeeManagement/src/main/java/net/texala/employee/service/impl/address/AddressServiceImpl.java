package net.texala.employee.service.impl.address;

import java.util.List;

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

}
