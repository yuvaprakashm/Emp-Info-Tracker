package net.texala.employee.department.service;
 
import org.springframework.data.domain.Page;
import net.texala.employee.department.model.Department;
import net.texala.employee.department.vo.DepartmentVo;
import net.texala.employee.enums.GenericStatus;

public interface DepartmentService {
	public Page<DepartmentVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy,
			String searchText);

	public Department findById(Long id);
	
	public DepartmentVo findDepartmentVoById(Long id);

	public DepartmentVo add(DepartmentVo departmentVo);

	public DepartmentVo update(DepartmentVo departmentVo, Long id);

	public void updateGenericStatus(GenericStatus status,Long id);

	public void delete(Long id);

	public String generateCsvContent();

}
