package net.texala.employee.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.texala.employee.address.model.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
