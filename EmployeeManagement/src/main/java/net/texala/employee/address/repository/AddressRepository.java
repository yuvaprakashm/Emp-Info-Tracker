package net.texala.employee.address.repository;

import static net.texala.employee.constants.Constants.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import net.texala.employee.address.model.Address;
import net.texala.employee.enums.GenericStatus;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {

	@Modifying
	@Query(UPDATE_ADDRESS_STATUS)
	public int updateStatus(GenericStatus status, Long id);

	List<Address> findByEmployeeId(Long employeeId);
}
