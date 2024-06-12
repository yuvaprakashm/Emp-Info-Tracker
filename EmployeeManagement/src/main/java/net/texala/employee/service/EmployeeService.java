package net.texala.employee.service;

import org.springframework.data.domain.Page;
import net.texala.employee.enums.GenericStatus;
import net.texala.employee.model.Employee;
import net.texala.employee.vo.EmployeeVo;

public interface EmployeeService {
	
	public Page<EmployeeVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy, String searchText);

	public Employee findById(Long id);

	public EmployeeVo findEmployeeVoById(Long id);
	
	public EmployeeVo add(EmployeeVo employeeVo);

	public void updateGenericStatus(GenericStatus status,Long id);
	
	public void delete(Long id);

	public EmployeeVo update(EmployeeVo employeeVo, Long id);

	public String generateCsvContent();

}
