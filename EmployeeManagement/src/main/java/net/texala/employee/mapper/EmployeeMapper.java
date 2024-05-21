package net.texala.employee.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import net.texala.employee.model.Employee;
import net.texala.employee.vo.EmployeeVo;

import java.util.List;

@Mapper
@Component
public interface EmployeeMapper {

    
    Employee toEntity(EmployeeVo employeeVo);
 
    EmployeeVo toDto(Employee employee);

    List<EmployeeVo> toDtos(List<Employee> employees);
    
}
