package net.texala.employee.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import net.texala.employee.model.Employee;
import net.texala.employee.vo.EmployeeVo;

@Mapper
@Component
public interface EmployeeMapper {

//    @Mapping(source = "deptId.", target = "department")
//    @Mapping(source = "addressId", target = "addresses")
    Employee toEntity(EmployeeVo employeeVo);

//    @Mapping(source = "department", target = "deptId")
//    @Mapping(source = "addresses", target = "addressId")
    EmployeeVo toDto(Employee employee);

//    @Mapping(source = "employee.department", target = "department")
//    @Mapping(source = "employee.department.deptName", target = "deptName")
//    EmployeeVo toDtoWithDepartment(Employee employee);

//    @Mapping(target = "department", qualifiedByName = "toDto")
//    List<EmployeeVo> toDtos(List<Employee> employees);
}
