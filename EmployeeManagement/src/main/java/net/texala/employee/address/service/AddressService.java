package net.texala.employee.address.service;

import java.util.List;
import org.springframework.data.domain.Page;
import net.texala.employee.address.model.Address;
import net.texala.employee.address.vo.AddressVo;
import net.texala.employee.enums.GenericStatus;

public interface AddressService {

	public Page<AddressVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy, String searchText);

	public Address findById(Long id);

	public AddressVo add(AddressVo addressVo);

	public AddressVo update(AddressVo addressVo, Long id);

	public void updateGenericStatus(GenericStatus status,Long id);

	public void delete(Long id);

	public String generateCsvContent();
	
	List<AddressVo> findAddressesByEmployeeId(Long employeeId);

}
