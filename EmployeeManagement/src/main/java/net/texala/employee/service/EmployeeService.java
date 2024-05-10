package net.texala.employee.service;

import java.util.List;

import org.springframework.data.domain.Page;
import net.texala.employee.model.Employee;
import net.texala.employee.vo.EmployeeVo;

 

public interface EmployeeService {
	public Page<EmployeeVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy, String searchText);

	public List<EmployeeVo> findAll();

	public Employee findById(Long id);

	public EmployeeVo add(EmployeeVo employeeVo);

	public EmployeeVo update(EmployeeVo employeeVo, Long id);

	public int active(Long id);

	public int deactive(Long id);

	public void delete(Long id);
		
}
