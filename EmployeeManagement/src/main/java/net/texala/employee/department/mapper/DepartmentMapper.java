package net.texala.employee.department.mapper;

import net.texala.employee.department.model.Department;
import net.texala.employee.department.vo.DepartmentVo;

public interface DepartmentMapper {
	DepartmentVo toVo(Department department);

	Department toEntity(DepartmentVo departmentVo);
}
