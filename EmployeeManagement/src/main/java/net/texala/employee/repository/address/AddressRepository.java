package net.texala.employee.repository.address;

import org.springframework.data.jpa.repository.JpaRepository;

import net.texala.employee.model.address.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
