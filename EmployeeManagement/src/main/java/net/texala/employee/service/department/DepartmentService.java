package net.texala.employee.service.department;

import java.util.List;

import net.texala.employee.model.department.Department;
 

public interface DepartmentService {
	List<Department> findAll();
}
