package net.texala.employee.address.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import net.texala.employee.address.model.Address;
import net.texala.employee.address.vo.AddressVo;

@Mapper
public interface AddressMapper {

    public Address toEntity(AddressVo addressVo);

    public AddressVo toDto(Address address);

    public List<AddressVo> toDtos(List<Address> addresses);
}
