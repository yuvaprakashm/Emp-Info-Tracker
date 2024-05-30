package net.texala.employee.address.service.impl;

import static net.texala.employee.constants.Constants.*;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.texala.employee.address.mapper.AddressMapper;
import net.texala.employee.address.model.Address;
import net.texala.employee.address.repository.AddressRepository;
import net.texala.employee.address.service.AddressService;
import net.texala.employee.address.vo.AddressVo;
import net.texala.employee.common.CommonSpecification;
import net.texala.employee.common.Utility;
import net.texala.employee.enums.GenericStatus;
import net.texala.employee.exception.Exception.ServiceException;
import net.texala.employee.service.EmployeeService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepo;
	@Autowired
	private AddressMapper addressMapper;
	@Autowired
	private EmployeeService employeeService;
	
	@Override
	public Page<AddressVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy, String searchText) {
		final Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Utility.sortByValues(sortBy)));
		final Specification<Address> joins = CommonSpecification.searchAddress(searchText, filterBy);
		final Page<Address> page = addressRepo.findAll(joins, pageable);
		return new PageImpl<>(addressMapper.toDtos(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	public Address findById(Long id) {
		return addressRepo.findById(id)
				.orElseThrow(() -> new ServiceException(ADDRESS_NOT_FOUND + id));
		 
	}

	@Override
	@Transactional
	public AddressVo add(AddressVo addressVo) {
		try {
			Address address = addressMapper.toEntity(addressVo);
			address.setEmployee(employeeService.findById(addressVo.getEmpId()));
			return addressMapper.toDto(addressRepo.save(address));
		 } catch (Exception e) {
			throw new RuntimeException(FAILED_ADD_ADD + e.getMessage());
		}
	}
	
	@Transactional
	@Override
	public void delete(Long id) {
		findById(id);
		addressRepo.updateStatus(GenericStatus.DELETED, id);
	}

	@Transactional
	@Override
	public void updateGenericStatus(GenericStatus status,Long id) {
		findById(id);
		addressRepo.updateStatus(status, id);
	}

	@Override
	public String generateCsvContent() {
		StringWriter writer = new StringWriter();
		try (@SuppressWarnings("deprecation")
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(ADDRESS_HEADER))) {
			Page<AddressVo> addressList = search(0, Integer.MAX_VALUE, "createdDate:asc", Strings.EMPTY,
					Strings.EMPTY);
			if (addressList != null && !addressList.isEmpty()) {
				for (AddressVo adddress : addressList) {
					csvPrinter.printRecord(adddress.getId(), adddress.getStreet(), adddress.getCity(),
							adddress.getState(), adddress.getZipcode(), adddress.getStatus(), adddress.getCreatedDate(),
							adddress.getDoorNumber(), adddress.getCountry(), adddress.getAddressType(),
							adddress.getLandMark());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}


    @Override
    public List<AddressVo> findAddressesByEmployeeId(Long employeeId) {
        List<Address> addresses = addressRepo.findByEmployeeId(employeeId);
        List<AddressVo> addressVo = new ArrayList<>();
        for (Address address : addresses) {
        	addressVo.add(addressMapper.toDto(address));
		}
        return addressVo;
    }

	@Override
	public AddressVo update(AddressVo addressVo, Long id) {
		findById(id);
		addressVo.setId(id);
		Address address = addressMapper.toEntity(addressVo);
		address.setEmployee(employeeService.findById(addressVo.getEmpId()));
		return addressMapper.toDto(addressRepo.save(address));
	}

}