package net.texala.employee.service.address;

import java.util.List;

import net.texala.employee.model.address.Address;
import net.texala.employee.vo.address.AddressVo;

 

public interface AddressService {
	List<AddressVo> findAll();

	AddressVo save(AddressVo address);
	
	String deleteById(int addressId);
	
	AddressVo update(AddressVo addressVo,int addressId);
	
	AddressVo updatePatch(AddressVo addressVo,int addressId);
	
	AddressVo activateRecord(Integer addressId);
	
	AddressVo deactivateRecord(Integer addressId); 
}
