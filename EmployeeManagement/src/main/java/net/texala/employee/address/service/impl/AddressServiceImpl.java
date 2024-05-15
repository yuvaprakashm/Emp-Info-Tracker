package net.texala.employee.address.service.impl;

import static net.texala.employee.constants.Constants.*;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.texala.employee.Specification.CommonSpecification;
import net.texala.employee.Util.Utility;
import net.texala.employee.address.mapper.AddressMapper;
import net.texala.employee.address.model.Address;
import net.texala.employee.address.repository.AddressRepository;
import net.texala.employee.address.service.AddressService;
import net.texala.employee.address.vo.AddressVo;
import net.texala.employee.enums.GenericStatus;
import net.texala.employee.exception.Exception.AddressNotFoundException;
@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressRepository repo;
	@Autowired
	private AddressMapper mapper;

	@Override
	public Page<AddressVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy, String searchText) {
		final Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Utility.sortByValues(sortBy)));
		final Specification<Address> joins = CommonSpecification.searchAddress(searchText, filterBy);
		final Page<Address> page = repo.findAll(joins, pageable);
		return new PageImpl<>(mapper.toDtos(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	public List<AddressVo> findAll() {
		return mapper.toDtos(repo.findAll());
	}

	@Override
	public Address findById(Long id) {
	    return repo.findById(id)
	               .orElseThrow(() -> new AddressNotFoundException(ADDRESS_NOT_FOUND + id));
	}


	@Override
	public AddressVo add(AddressVo addressVo) {
		Address address = new Address();
		BeanUtils.copyProperties(addressVo, address);
		return mapper.toDto(repo.save(address));
	}

	@Override
	public AddressVo update(AddressVo addressVo, Long id, boolean partialUpdate) {
		Address existingAddress = repo.findById(id)
				.orElseThrow(() -> new RuntimeException(ADDRESS_NOT_FOUND + id));
		if (partialUpdate) {
			if (addressVo.getStreet() != null) {
				existingAddress.setStreet(addressVo.getStreet());
			}
			if (addressVo.getCity() != null) {
				existingAddress.setCity(addressVo.getCity());
			}
			if (addressVo.getZipcode() != null) {
				existingAddress.setZipcode(addressVo.getZipcode());
			}

		} else

		{
			existingAddress.setStreet(addressVo.getStreet());
			existingAddress.setCity(addressVo.getCity());
			existingAddress.setState(addressVo.getState());
			existingAddress.setZipcode(addressVo.getZipcode());
			existingAddress.setStatus(addressVo.getStatus());
			existingAddress.setCreatedDate(addressVo.getCreatedDate());
			existingAddress.setDoorNumber(addressVo.getDoorNumber());
			existingAddress.setCountry(addressVo.getCountry());
			existingAddress.setAddressType(addressVo.getAddressType());
			existingAddress.setLandMark(addressVo.getLandMark());

		}
		Address updatedAddress = repo.save(existingAddress);
		return mapper.toDto(updatedAddress);

	}

	@Transactional
	@Override
	public int active(Long id) {
		return repo.updateStatus(GenericStatus.ACTIVE, id);
	}

	@Transactional
	@Override
	public int deactive(Long id) {
		return repo.updateStatus(GenericStatus.DEACTIVE, id);
	}

	@Override
	public void delete(Long id) {
		findById(id);
		repo.deleteById(id);
	}
	@Override
	public String generateCsvContent() {
	    StringWriter writer = new StringWriter();
	    try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
	            .withHeader(ADDRESS_HEADER))) {

	        List<AddressVo> addressList = findAll();
	        if (addressList != null && !addressList.isEmpty()) {
	            for (AddressVo adddress : addressList) {
	                csvPrinter.printRecord(
	                		adddress.getId(),
	                		adddress.getStreet(),
	                		adddress.getCity(),
	                		adddress.getState(),
	                		adddress.getZipcode(),
	                		adddress.getStatus(),
	                		adddress.getCreatedDate(),
	                		adddress.getDoorNumber(),
	                		adddress.getCountry(),
	                		adddress.getAddressType(),
	                		adddress.getLandMark()
	                );
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return writer.toString();
	}
}
