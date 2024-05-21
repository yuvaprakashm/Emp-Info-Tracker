package net.texala.employee.address.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import net.texala.employee.address.model.Address;
import net.texala.employee.address.vo.AddressVo;

import java.util.List;

@Mapper
@Component
public interface AddressMapper {
	
   
    Address toEntity(AddressVo addressVo);

    AddressVo toDto(Address address);

    List<AddressVo> toDtos(List<Address> addresses);
}
