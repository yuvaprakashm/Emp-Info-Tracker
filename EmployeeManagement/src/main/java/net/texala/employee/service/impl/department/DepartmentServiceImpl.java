package net.texala.employee.service.impl.department;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.texala.employee.model.department.Department;
import net.texala.employee.repository.department.DepartmentRepository;
import net.texala.employee.service.department.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public List<Department> findAll() {

		return (List<Department>) departmentRepository.findAll();
	}

	@Override
	public Department save(Department department) {

		return departmentRepository.save(department);
	}

	@Override
	public boolean deleteById(int deptId) {
		  departmentRepository.deleteById(deptId);
		return false;
	}

	@Override
	public Department update(Department department, int deptId) {
		Department existingDepartment = departmentRepository.findById(deptId).orElseThrow(() -> new  RuntimeException("Department with Id" + deptId + "not found"));
		existingDepartment.setDeptName(department.getDeptName());
		return departmentRepository.save(existingDepartment);
	}

	@Override
	public Department updatePatch(Department department, int deptId) {
		Department existingDepartment = departmentRepository.findById(deptId).orElseThrow(() -> new RuntimeException("Department with Id " + deptId + " not found"));
		 if (department.getDeptName() != null) {
			existingDepartment.setDeptName(department.getDeptName());
		}
		return departmentRepository.save(existingDepartment);
	}

	@Override
	public Department activateRecord(Integer deptId) {
		 Department dept = departmentRepository.findById(deptId).orElseThrow(() -> new NoSuchElementException("Department with ID " + deptId + " not found"));
		 if (dept.getActive() == null || !dept.getActive())  {
			dept.setActive(true);
			return departmentRepository.save(dept);
		}else {
		 throw new RuntimeException("Record is already active");
		}
	}

	@Override
	public Department deactivateRecord(Integer deptId) {
		
		Department dept = departmentRepository.findById(deptId)
				.orElseThrow(() -> new NoSuchElementException("Department with ID " + deptId + " not found"));
		if (dept.getActive() != null && dept.getActive()) {
			dept.setActive(false);
			return departmentRepository.save(dept);
		} else {
			throw new RuntimeException("Record is already deactive");
		}
	}

}
