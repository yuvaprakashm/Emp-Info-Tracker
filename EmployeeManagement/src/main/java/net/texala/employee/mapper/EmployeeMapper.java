package net.texala.employee.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import net.texala.employee.model.Employee;
import net.texala.employee.vo.EmployeeVo;

@Mapper
@Component
public interface EmployeeMapper {
	
	Employee toEntity(EmployeeVo employeeVo);
	
	EmployeeVo toDto(Employee employee);
	
	List<EmployeeVo> toDtos(List<Employee> employees);
}
