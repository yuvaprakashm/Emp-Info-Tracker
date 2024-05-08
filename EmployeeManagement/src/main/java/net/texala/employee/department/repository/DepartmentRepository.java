package net.texala.employee.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.texala.employee.department.model.Department;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{

}
