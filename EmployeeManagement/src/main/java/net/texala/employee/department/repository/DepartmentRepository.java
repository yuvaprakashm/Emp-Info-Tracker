package net.texala.employee.department.repository;

import static net.texala.employee.constants.Constants.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import net.texala.employee.department.model.Department;
import net.texala.employee.enums.GenericStatus;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department>{
	
	@Modifying
	@Query(UPDATE_DEPARTMENT_STATUS)
	public int updateStatus(GenericStatus status, Long id);
	
	boolean existsByDeptName(String deptName);
	
	
}
