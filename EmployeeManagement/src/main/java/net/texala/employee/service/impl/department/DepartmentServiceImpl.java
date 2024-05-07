package net.texala.employee.service.impl.department;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.texala.department.mapper.DepartmentMapper;
import net.texala.employee.mapper.EmployeeMapper;
import net.texala.employee.model.department.Department;
import net.texala.employee.model.employee.Employee;
import net.texala.employee.repository.department.DepartmentRepository;
import net.texala.employee.service.department.DepartmentService;
import net.texala.employee.vo.department.DepartmentVo;
import net.texala.employee.vo.employee.EmployeeVo;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private DepartmentMapper departmentMapper;

	@Override
	public List<DepartmentVo> findAll() {
		List<Department> department = departmentRepository.findAll();
		if (department.isEmpty()) {

			return Collections.emptyList();
		}
		return department.stream().map(departmentMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public DepartmentVo save(DepartmentVo departmentVo) {
		Department department = departmentMapper.toEntity(departmentVo);
		department = departmentRepository.save(department);
		if (department != null) {
			return departmentMapper.toVo(department);
		} else {
			return null;
		}
	}

	@Override
	public String deleteById(int deptId) {
		try {
			departmentRepository.deleteById(deptId);
			return "Department " + deptId + " deleted successfully";
		} catch (Exception e) {
			return "Department with ID " + deptId + " not found";
		}
	}

	@Override
	public DepartmentVo update(DepartmentVo departmentVo, int deptId) {
		try {
			Department existingDepartment = departmentRepository.findById(deptId)
					.orElseThrow(() -> new RuntimeException("Department with Id " + deptId + " not found"));
			existingDepartment.setDeptName(departmentVo.getDeptName());
			existingDepartment = departmentRepository.save(existingDepartment);
			return departmentMapper.toVo(existingDepartment);
		} catch (RuntimeException e) {
			throw new RuntimeException("Error updating department with ID " + deptId + ": " + e.getMessage(), e);
		}

	}

	@Override
	public DepartmentVo updatePatch(DepartmentVo departmentVo, int deptId) {
		try {
			Department existingDepartment = departmentRepository.findById(deptId)
					.orElseThrow(() -> new RuntimeException("Department with Id " + deptId + " not found"));
			existingDepartment.setDeptName(departmentVo.getDeptName());
			existingDepartment = departmentRepository.save(existingDepartment);
			return departmentMapper.toVo(existingDepartment);
		} catch (RuntimeException e) {
			throw new RuntimeException("Error updating department with ID " + deptId + ": " + e.getMessage(), e);
		}
	}

	@Override
	public DepartmentVo activateRecord(Integer deptId) {
		try {
			Department department = departmentRepository.findById(deptId)
					.orElseThrow(() -> new NoSuchElementException("Department with ID " + deptId + " not found"));
			if (department.getActive() == null || !department.getActive()) {
				department.setActive(true);
				return departmentMapper.toVo(departmentRepository.save(department));
			} else {
				throw new RuntimeException("Record is already active");
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("department with ID " + deptId + " not found");
		} catch (RuntimeException e) {
			throw new RuntimeException("Error activating department with ID " + deptId + ": " + e.getMessage(), e);
		}
	}

	@Override
	public DepartmentVo deactivateRecord(Integer deptId) {
		try {
			Department department = departmentRepository.findById(deptId)
					.orElseThrow(() -> new NoSuchElementException("Department with ID " + deptId + " not found"));
			if (department.getActive() != null && department.getActive()) {
				department.setActive(false);
				return departmentMapper.toVo(departmentRepository.save(department));
			} else {
				throw new RuntimeException("Record is already deactive");
			}
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("department with ID " + deptId + " not found");
		} catch (RuntimeException e) {
			throw new RuntimeException("Error deactivating department with ID " + deptId + ": " + e.getMessage(), e);
		}
	}
}
