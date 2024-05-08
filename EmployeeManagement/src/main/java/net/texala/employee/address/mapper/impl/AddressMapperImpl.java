package net.texala.employee.address.mapper.impl;

import org.springframework.stereotype.Component;

import net.texala.employee.address.mapper.AddressMapper;
import net.texala.employee.address.model.Address;
import net.texala.employee.address.vo.AddressVo;
@Component
public class AddressMapperImpl implements AddressMapper {

	@Override
	public AddressVo toVo(Address address) {
		AddressVo vo = new AddressVo();
		vo.setAddressId(address.getAddressId());
		vo.setStreet(address.getStreet());
		vo.setCity(address.getCity());
		vo.setState(address.getStreet());
		vo.setZipcode(address.getZipcode());
		return vo;
	}

	@Override
	public Address toEntity(AddressVo addressVo) {
		Address address = new Address();
		address.setAddressId(addressVo.getAddressId());
		address.setStreet(addressVo.getStreet());
		address.setCity(addressVo.getCity());
		address.setState(addressVo.getState());
		address.setZipcode(addressVo.getZipcode());
		return address;
	}
}
