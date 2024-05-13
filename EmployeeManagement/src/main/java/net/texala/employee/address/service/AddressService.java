package net.texala.employee.address.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.texala.employee.address.model.Address;
import net.texala.employee.address.vo.AddressVo;

public interface AddressService {

	public Page<AddressVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy, String searchText);

	public List<AddressVo> findAll();

	public Address findById(Long id);

	public AddressVo add(AddressVo addressVo);

	public AddressVo update(AddressVo addressVo,Long id,boolean partialUpdate);

	public int active(Long id);

	public int deactive(Long id);

	public void delete(Long id);

}
