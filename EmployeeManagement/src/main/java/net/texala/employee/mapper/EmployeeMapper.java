package net.texala.employee.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import net.texala.employee.address.mapper.AddressMapper;
import net.texala.employee.department.mapper.DepartmentMapper;
import net.texala.employee.model.Employee;
import net.texala.employee.vo.EmployeeVo;

@Mapper(componentModel = "spring", uses = {DepartmentMapper.class,AddressMapper.class})
@Component
public interface EmployeeMapper {
	
    Employee toEntity(EmployeeVo employeeVo);
    
    @Mapping(target ="deptId", source="employee.department.deptId")  //in employeeVo we have deptId
    EmployeeVo toDto(Employee employee);

    List<EmployeeVo> toDtos(List<Employee> employees);

    
}
