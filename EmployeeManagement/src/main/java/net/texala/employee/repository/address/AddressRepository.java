package net.texala.employee.repository.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.texala.employee.model.address.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
