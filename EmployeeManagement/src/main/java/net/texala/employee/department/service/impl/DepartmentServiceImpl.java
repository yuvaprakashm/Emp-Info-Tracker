package net.texala.employee.department.service.impl;

import java.util.List;

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
import net.texala.employee.department.mapper.DepartmentMapper;
import net.texala.employee.department.model.Department;
import net.texala.employee.department.repository.DepartmentRepository;
import net.texala.employee.department.service.DepartmentService;
import net.texala.employee.department.vo.DepartmentVo;
import net.texala.employee.enums.GenericStatus;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository repo;
	@Autowired
	private DepartmentMapper mapper;

	@Override
	public Page<DepartmentVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy,
			String searchText) {
		final Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Utility.sortByValues(sortBy)));
		final Specification<Department> joins = CommonSpecification.searchDepartment(searchText, filterBy);
		final Page<Department> page = repo.findAll(joins, pageable);
		return new PageImpl<>(mapper.toDtos(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	public Department findById(Long id) {
		return repo.findById(id).orElseThrow(() -> new RuntimeException("Id not Found"));
	}

	@Override
	public DepartmentVo add(DepartmentVo departmentVo) {
		Department department = new Department();
		BeanUtils.copyProperties(departmentVo, department);
		return mapper.toDto(repo.save(department));
	}

	@Override
	public DepartmentVo update(DepartmentVo departmentVo, Long id) {
		Department  existingDepartment = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
		existingDepartment.setDeptName(departmentVo.getDeptName());
		Department updatedDepartment = repo.save(existingDepartment);
		return mapper.toDto(updatedDepartment);
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
	public List<DepartmentVo> findAll() {
		return mapper.toDtos(repo.findAll());
	}
}
