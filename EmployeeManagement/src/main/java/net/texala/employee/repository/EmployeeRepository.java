package net.texala.employee.repository;

import static net.texala.employee.constants.Constants.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import net.texala.employee.enums.GenericStatus;
import net.texala.employee.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>{
		
	@Modifying
	@Query(UPDATE_EMPLOYEE_STATUS)
	public int updateStatus(GenericStatus status, Long id);
	
}
