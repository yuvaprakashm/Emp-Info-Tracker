package net.texala.employee.address.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.texala.employee.address.model.Address;
import net.texala.employee.address.vo.AddressVo;
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>, JpaSpecificationExecutor<Address> {
	@Query("SELECT a FROM Address a WHERE (:filterBy IS NULL OR " +
	           "(a.city LIKE %:searchText% OR a.state LIKE %:searchText%)) " +
	           "AND (:searchText IS NULL OR " +
	           "(a.city LIKE %:searchText% OR a.state LIKE %:searchText%))")
	    Page<AddressVo> findByFilterAndSearch(@Param("filterBy") String filterBy,
	                                        @Param("searchText") String searchText,
	                                        Pageable pageable);

	@Query("SELECT a FROM Address a WHERE lower(a.city) LIKE lower(concat('%', :searchText, '%')) " +
	           "OR lower(a.state) LIKE lower(concat('%', :searchText, '%'))")
	    Page<AddressVo> findBySearchText(@Param("searchText") String searchText, Pageable pageable);

}
