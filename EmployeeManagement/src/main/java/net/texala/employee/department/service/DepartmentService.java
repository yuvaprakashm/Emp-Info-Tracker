package net.texala.employee.department.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.texala.employee.department.model.Department;
import net.texala.employee.department.vo.DepartmentVo;
 

public interface DepartmentService {
	public Page<DepartmentVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy, String searchText);

	public List<DepartmentVo> findAll();

	public Department findById(Long id);

	public DepartmentVo add(DepartmentVo departmentVo);

	public DepartmentVo update(DepartmentVo departmentVo, Long id);

	public int active(Long id);

	public int deactive(Long id);

	public void delete(Long id);

}
