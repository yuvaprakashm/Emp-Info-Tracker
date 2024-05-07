package net.texala.employee.service.department;

import java.util.List;

import net.texala.employee.model.department.Department;
import net.texala.employee.vo.department.DepartmentVo;

public interface DepartmentService {
	List<DepartmentVo> findAll();

	DepartmentVo save(DepartmentVo departmentVo);

	String deleteById(int deptId);

	DepartmentVo update(DepartmentVo departmentVo, int deptId);

	DepartmentVo updatePatch(DepartmentVo departmentVo, int deptId);

	DepartmentVo activateRecord(Integer deptId);

	DepartmentVo deactivateRecord(Integer deptId);

}
