package net.texala.employee.repository.department;

import org.springframework.data.jpa.repository.JpaRepository;

import net.texala.employee.model.department.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{

}
