package net.texala.employee.department.repository;

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
	@Query("update Department a set a.status=:status where a.id=:id")
	public int updateStatus(GenericStatus status, Long id);
}
