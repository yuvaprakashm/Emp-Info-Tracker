package net.texala.employee.service.department;

import java.util.List;

import net.texala.employee.model.department.Department;
 

public interface DepartmentService {
	List<Department> findAll();
	
	Department save(Department department);
	
	boolean deleteById(int deptId);
	
	Department update(Department department, int deptId);
	
	Department updatePatch(Department department, int deptId);
	
	Department activateRecord(Integer deptId);
	
	Department deactivateRecord(Integer deptId);
	
}
