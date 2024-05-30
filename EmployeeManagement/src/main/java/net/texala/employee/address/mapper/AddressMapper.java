package net.texala.employee.address.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import net.texala.employee.address.model.Address;
import net.texala.employee.address.vo.AddressVo;

@Mapper(componentModel = "spring")
@Component
public interface AddressMapper {
   
	
    Address toEntity(AddressVo addressVo);

    @Mapping(target = "empId", source = "employee.id")
    AddressVo toDto(Address address);

    List<AddressVo> toDtos(List<Address> addresses);
    
    
}
 