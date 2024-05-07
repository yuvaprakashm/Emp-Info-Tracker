package net.texala.employee.service.employee;

import java.util.List;

import net.texala.employee.vo.employee.EmployeeVo;

 

public interface EmployeeService {
	List<EmployeeVo> findAll();

	EmployeeVo save(EmployeeVo employeeVo);

	String deleteById(int id);

	EmployeeVo update(EmployeeVo employeeVo, int id);

	EmployeeVo updatePatch(EmployeeVo employeeVo, int id);

	EmployeeVo activateRecord(Integer id);

	EmployeeVo deactivateRecord(Integer id);
}
