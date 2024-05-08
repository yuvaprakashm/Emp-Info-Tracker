package net.texala.employee.address.mapper;

import net.texala.employee.address.model.Address;
import net.texala.employee.address.vo.AddressVo;

public interface AddressMapper {
	AddressVo toVo(Address address);

	Address toEntity(AddressVo addressVo);
}
