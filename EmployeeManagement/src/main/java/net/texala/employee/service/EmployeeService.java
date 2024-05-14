package net.texala.employee.service;

import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import com.opencsv.exceptions.CsvException;
import net.texala.employee.model.Employee;
import net.texala.employee.vo.EmployeeVo;

public interface EmployeeService {
	public Page<EmployeeVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy, String searchText);

	public List<EmployeeVo> findAll();

	public Employee findById(Long id);

	public EmployeeVo add(EmployeeVo employeeVo);

	public int active(Long id);

	public int deactive(Long id);

	public void delete(Long id);

	public EmployeeVo update(EmployeeVo employeeVo, Long id, boolean partialUpdate);

	public void save(String filename) throws IOException, CsvException;

	 

	public String generateCsvContent();

}
