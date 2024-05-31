package net.texala.employee.department.service.impl;

import static net.texala.employee.constants.Constants.*;
import java.io.IOException;
import java.io.StringWriter;
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
import net.texala.employee.common.CommonSpecification;
import net.texala.employee.common.Utility;
import net.texala.employee.department.mapper.DepartmentMapper;
import net.texala.employee.department.model.Department;
import net.texala.employee.department.repository.DepartmentRepository;
import net.texala.employee.department.service.DepartmentService;
import net.texala.employee.department.vo.DepartmentVo;
import net.texala.employee.enums.GenericStatus;
import net.texala.employee.exception.Exception.ServiceException;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepo;
	@Autowired
	private DepartmentMapper departmentMapper;
	
	@Override
	public Page<DepartmentVo> search(Integer pageNo, Integer pageSize, String sortBy, String filterBy,
			String searchText) {
		final Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Utility.sortByValues(sortBy)));
		final Specification<Department> joins = CommonSpecification.searchDepartment(searchText, filterBy);
		final Page<Department> page = departmentRepo.findAll(joins, pageable);
		return new PageImpl<>(departmentMapper.toDtos(page.getContent()), pageable, page.getTotalElements());
	}

	@Override
	public Department findById(Long id) {
		return departmentRepo.findById(id)
				.orElseThrow(() -> new ServiceException(DEPARTMENT_NOT_FOUND + id));
		 
	}
 
	@Transactional
	@Override
	public DepartmentVo add(DepartmentVo departmentVo) {
	    try {
	    	if (departmentRepo.existsByDeptName(departmentVo.getDeptName())) {
				throw new IllegalArgumentException(DEPT_WITH_NAME + departmentVo.getDeptName() + ALREADY_EXISTS);
			}
	    	 Department department = departmentMapper.toEntity(departmentVo);
	         departmentRepo.save(department);
	         return departmentMapper.toDto(department);
	    } catch (Exception e) {
	        throw new RuntimeException(FAILED_ADD_DEPT + e.getMessage());
	    }
	}

	@Transactional
	@Override
	public DepartmentVo update(DepartmentVo departmentVo, Long id) {
		findById(id);
		Department dept = departmentMapper.toEntity(departmentVo);
		return departmentMapper.toDto(departmentRepo.save(dept));
}
		 
	@Transactional
	@Override
	public void delete(Long id) {
		findById(id);
		departmentRepo.updateStatus(GenericStatus.DELETED, id);

	}

	@Transactional
	@Override
	public void updateGenericStatus(GenericStatus status,Long id) {
		findById(id);
		departmentRepo.updateStatus(status, id);
	}

	@Override
	public String generateCsvContent() {
		StringWriter writer = new StringWriter();
		try (@SuppressWarnings("deprecation")
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(DEPARTMENT_HEADER))) {
			Page<DepartmentVo> departmentList =  search(0, Integer.MAX_VALUE, "createdDate:asc", Strings.EMPTY,
					Strings.EMPTY);
			if (departmentList != null && !departmentList.isEmpty()) {
				for (DepartmentVo department : departmentList) {
					csvPrinter.printRecord(department.getDeptId(), department.getDeptName(),
							department.getCreatedDate(), department.getStatus(), department.getDeptContactNumber(),
							department.getEmailAddress(), department.getBudget());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	@Override
	public DepartmentVo findDepartmentVoById(Long id) {
		return departmentMapper.toDto(findById(id));
	}
}
