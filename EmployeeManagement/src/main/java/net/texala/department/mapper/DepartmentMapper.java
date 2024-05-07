package net.texala.department.mapper;

import net.texala.employee.model.department.Department;
import net.texala.employee.vo.department.DepartmentVo;

public interface DepartmentMapper {
	DepartmentVo toVo(Department department);

	Department toEntity(DepartmentVo departmentVo);
}
