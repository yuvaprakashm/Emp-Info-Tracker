package net.texala.employee.department.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import net.texala.employee.department.model.Department;
import net.texala.employee.department.vo.DepartmentVo;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
	
	public Department toEntity(DepartmentVo departmentVo);
	
	 @Mapping(target = "employees", ignore = true)
	public DepartmentVo toDto(Department department);

	public List<DepartmentVo> toDtos(List<Department> departments);
}
