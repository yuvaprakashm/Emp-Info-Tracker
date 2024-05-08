package net.texala.employee.department.service;

import java.util.List;

import net.texala.employee.department.model.Department;
import net.texala.employee.department.vo.DepartmentVo;

public interface DepartmentService {
	List<DepartmentVo> findAll();

	DepartmentVo save(DepartmentVo departmentVo);

	String deleteById(int deptId);

	DepartmentVo update(DepartmentVo departmentVo, int deptId);

	DepartmentVo updatePatch(DepartmentVo departmentVo, int deptId);

	DepartmentVo activateRecord(Integer deptId);

	DepartmentVo deactivateRecord(Integer deptId);

}
