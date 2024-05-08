package net.texala.employee.mapper;

import net.texala.employee.model.Employee;
import net.texala.employee.vo.EmployeeVo;
 

public interface EmployeeMapper {

	EmployeeVo toVo(Employee employee);

	Employee toEntity(EmployeeVo employeeVo);
}
