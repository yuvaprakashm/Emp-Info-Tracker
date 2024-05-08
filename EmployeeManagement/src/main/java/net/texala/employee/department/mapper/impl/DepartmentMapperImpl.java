package net.texala.employee.department.mapper.impl;

import org.springframework.stereotype.Component;

import net.texala.employee.department.mapper.DepartmentMapper;
import net.texala.employee.department.model.Department;
import net.texala.employee.department.vo.DepartmentVo;
@Component
public class DepartmentMapperImpl implements DepartmentMapper {
	@Override
	public DepartmentVo toVo(Department department) {
		DepartmentVo vo = new DepartmentVo();
		vo.setDeptId(department.getDeptId());
		vo.setDeptName(department.getDeptName());
		vo.setActive(department.getActive());
		return vo;

	}

	@Override
	public Department toEntity(DepartmentVo departmentVo) {
		Department department = new Department();
		department.setDeptId(departmentVo.getDeptId());
		department.setDeptName(departmentVo.getDeptName());
		department.setActive(departmentVo.getActive());
		return department;
	}

}
