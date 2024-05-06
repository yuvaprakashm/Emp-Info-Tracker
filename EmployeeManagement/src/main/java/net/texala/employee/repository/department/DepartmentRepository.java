package net.texala.employee.repository.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.texala.employee.model.department.Department;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{

}
