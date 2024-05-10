package net.texala.employee.service.impl;

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
import net.texala.employee.enums.GenericStatus;
import net.texala.employee.mapper.EmployeeMapper;
import net.texala.employee.model.Employee;
import net.texala.employee.repository.EmployeeRepository;
import net.texala.employee.service.EmployeeService;
import net.texala.employee.vo.EmployeeVo;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository repo;
	@Autowired
	private EmployeeMapper mapper;

	@Override
	public Page<EmployeeVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy,
			String searchText) {
		final Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Utility.sortByValues(sortBy)));
		final Specification<Employee> joins = CommonSpecification.searchEmployee(searchText, filterBy);
		final Page<Employee> page = repo.findAll(joins, pageable);
		return new PageImpl<>(mapper.toDtos(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	public Employee findById(Long id) {
		return repo.findById(id).orElseThrow(() -> new RuntimeException("Id not Found"));
	}

	@Override
	public EmployeeVo add(EmployeeVo employeeVo) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeVo, employee);
		return mapper.toDto(repo.save(employee));
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
	public List<EmployeeVo> findAll() {
		return mapper.toDtos(repo.findAll());
	}

	@Override
	public EmployeeVo update(EmployeeVo employeeVo, Long id) {
		Employee existingEmployee = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
		existingEmployee.setFirstName(employeeVo.getFirstName());
		existingEmployee.setLastName(employeeVo.getLastName());
		existingEmployee.setEmail(employeeVo.getEmail());
		existingEmployee.setAge(employeeVo.getAge());
		existingEmployee.setStatus(employeeVo.getStatus());
		existingEmployee.setCreatedDate(employeeVo.getCreatedDate());
		Employee updatedEmployee = repo.save(existingEmployee);
		return mapper.toDto(updatedEmployee);
	}

}