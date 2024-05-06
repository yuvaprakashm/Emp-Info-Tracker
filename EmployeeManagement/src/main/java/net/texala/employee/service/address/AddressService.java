package net.texala.employee.service.address;

import java.util.List;

import net.texala.employee.model.address.Address;

 

public interface AddressService {
	List<Address> findAll();

	Address save(Address address);
	
	boolean deleteById(int addressId);
	
	Address update(Address address,int addressId);
	
	Address updatePatch(Address address,int addressId);
	
	Address activateRecord(Integer addressId);
	
	Address deactivateRecord(Integer addressId); 
}
