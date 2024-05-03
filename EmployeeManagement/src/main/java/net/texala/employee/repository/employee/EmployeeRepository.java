package net.texala.employee.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.texala.employee.model.employee.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
