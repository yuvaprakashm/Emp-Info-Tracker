package net.texala.employee.mapper.address;

import net.texala.employee.model.address.Address;
import net.texala.employee.vo.address.AddressVo;

public interface AddressMapper {
	AddressVo toVo(Address address);

	Address toEntity(AddressVo addressVo);
}
