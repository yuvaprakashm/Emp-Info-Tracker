package net.texala.employee.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import net.texala.employee.model.employee.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
