package net.texala.employee.address.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import net.texala.employee.address.mapper.AddressMapper;
import net.texala.employee.address.model.Address;
import net.texala.employee.address.repository.AddressRepository;
import net.texala.employee.address.service.AddressService;
import net.texala.employee.address.vo.AddressVo;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private AddressMapper addressMapper;

	@Override
	public List<AddressVo> findAll() {
		List<Address> address = addressRepository.findAll();
		if (address.isEmpty()) {

			return Collections.emptyList();
		}
		return address.stream().map(addressMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public AddressVo save(AddressVo addressVo) {
		Address address = addressMapper.toEntity(addressVo);
		address = addressRepository.save(address);
		if (address != null) {
			return addressMapper.toVo(address);
		} else {
			return null;
		}

	}

	@Override
	public String deleteById(int addressId) {
		try {
			addressRepository.deleteById(addressId);
			return "Address " + addressId + " deleted successfully";
		} catch (EmptyResultDataAccessException e) {
			return "Address with ID " + addressId + " not found";
		}
	}

	@Override
	public AddressVo update(AddressVo addressVo, int addressId) {
		try {
			Address existingAddress = addressRepository.findById(addressId)
					.orElseThrow(() -> new RuntimeException("Address with Id" + addressId + "not found"));
			existingAddress.setStreet(addressVo.getStreet());
			existingAddress.setCity(addressVo.getCity());
			existingAddress.setState(addressVo.getState());
			existingAddress.setZipcode(addressVo.getZipcode());
			existingAddress = addressRepository.save(existingAddress);
			return addressMapper.toVo(existingAddress);

		} catch (RuntimeException e) {
			throw new RuntimeException("Error updating employee with ID " + addressId + ": " + e.getMessage(), e);
		}
	}

	@Override
	public AddressVo updatePatch(AddressVo addressVo, int addressId) {
		try {
			Address existingAddress = addressRepository.findById(addressId)
					.orElseThrow(() -> new RuntimeException("Address with Id " + addressId + " not found"));
			existingAddress.setStreet(addressVo.getStreet());
			existingAddress.setCity(addressVo.getCity());
			existingAddress.setState(addressVo.getState());
			existingAddress = addressRepository.save(existingAddress);
			return addressMapper.toVo(existingAddress);
		} catch (RuntimeException e) {
			throw new RuntimeException("Error updating address with ID " + addressId + ": " + e.getMessage(), e);
		}
	}

	@Override
	public AddressVo activateRecord(Integer addressId) {
		try {
			Address add = addressRepository.findById(addressId)
					.orElseThrow(() -> new NoSuchElementException("Address with ID " + addressId + " not found"));
			if (add.getActive() == null || !add.getActive()) {
				add.setActive(true);
				return addressMapper.toVo(addressRepository.save(add));
			} else {
				throw new RuntimeException("Record is already active");
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Address with ID " + addressId + " not found");
		} catch (RuntimeException e) {
			throw new RuntimeException("Error activating employee with ID " + addressId + ": " + e.getMessage(), e);
		}
	}

	@Override
	public AddressVo deactivateRecord(Integer addressId) {
		try {
			Address add = addressRepository.findById(addressId)
					.orElseThrow(() -> new NoSuchElementException("Address with ID " + addressId + " not found"));
			if (add.getActive() != null && add.getActive()) {
				add.setActive(false);
				return addressMapper.toVo(addressRepository.save(add));
			} else {
				throw new RuntimeException("Record is already deactive");
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Employee with ID " + addressId + " not found");
		} catch (RuntimeException e) {
			throw new RuntimeException("Error deactivating employee with ID " + addressId + ": " + e.getMessage(), e);
		}

	}

	@Override
	public Page<AddressVo> search(AddressVo addressVo, int pageNo, int pageSize, String sortBy) 
	{
		try
		{
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Specification<Address> spec = (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (addressVo.getStreet() != null) {
				predicates.add(criteriaBuilder.equal(root.get("street"), addressVo.getStreet()));
			}
			if (addressVo.getCity() != null) {
				predicates.add(criteriaBuilder.equal(root.get("city"), addressVo.getCity()));
			}
			if (addressVo.getState() != null) {
	            predicates.add(criteriaBuilder.equal(root.get("state"), addressVo.getState()));
	        }
	        if (addressVo.getZipcode() != null) {
	            predicates.add(criteriaBuilder.equal(root.get("zipcode"), addressVo.getZipcode()));
	        }
	        if (addressVo.getActive() != null) {
	            predicates.add(criteriaBuilder.equal(root.get("active"), addressVo.getActive()));
	        }

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};

		Page<Address> result = addressRepository.findAll(spec, pageable);

		return result.map(addressMapper::toVo);
	}catch (Exception e) {
		throw new RuntimeException("An error occurred while searching for addresses", e);
	}
}
	@Override
	public Page<AddressVo> searchRecords(int pageNo, int pageSize, String sortBy, String filterBy, String searchText) {
        // Create Pageable object for pagination and sorting
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy.split(":")[1])
                .descending().equals(sortBy.split(":")[1]) ? Direction.DESC : Direction.ASC, sortBy.split(":")[0]);

        // Perform the search operation based on provided parameters
        if (filterBy != null && searchText != null) {
            // Apply additional filters if both filterBy and searchText are provided
            return addressRepository.findByFilterAndSearch(filterBy, searchText, pageable);
        } else if (searchText != null) {
            // Apply only search text filter
            return addressRepository.findBySearchText(searchText, pageable);
        } else {
            // Return all records if no search criteria are provided
        	return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
    }
}