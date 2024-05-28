package net.texala.employee.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import net.texala.employee.department.mapper.DepartmentMapper;
import net.texala.employee.model.Employee;
import net.texala.employee.vo.EmployeeVo;

@Mapper(componentModel = "spring", uses = DepartmentMapper.class)
@Component
public interface EmployeeMapper {

    
    Employee toEntity(EmployeeVo employeeVo);
 
    EmployeeVo toDto(Employee employee);

    List<EmployeeVo> toDtos(List<Employee> employees);

    
}
