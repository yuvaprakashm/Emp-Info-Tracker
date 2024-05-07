package net.texala.employee.mapper.impl.employee;

import org.springframework.stereotype.Component;
import net.texala.employee.enums.Gender;
import net.texala.employee.mapper.EmployeeMapper;
import net.texala.employee.model.employee.Employee;
import net.texala.employee.vo.employee.EmployeeVo;
 

@Component
public class EmployeeMapperImpl implements EmployeeMapper {

	@Override
	public EmployeeVo toVo(Employee employee) {
		EmployeeVo vo = new EmployeeVo();
		vo.setId(employee.getId());
		vo.setFirstName(employee.getFirstName());
		vo.setLastName(employee.getLastName());
		vo.setAge(employee.getAge());
		vo.setEmail(employee.getEmail());
		vo.setGender(mapGender(employee.getGender()));
		vo.setSalary(employee.getSalary());
		vo.setActive(employee.getActive());

		return vo;
	}

	private static Gender mapGender(String dbGender) {

		if ("MALE".equalsIgnoreCase(dbGender)) {
			return Gender.MALE;
		} else if ("FEMALE".equalsIgnoreCase(dbGender)) {
			return Gender.FEMALE;
		} else {
			return Gender.UNKNOWN;
		}
	}

	@Override
	public Employee toEntity(EmployeeVo employeeVo) {
		Employee employee = new Employee();
		employee.setId(employeeVo.getId());
		employee.setFirstName(employeeVo.getFirstName());
		employee.setLastName(employeeVo.getLastName());
		employee.setAge(employeeVo.getAge());
		employee.setEmail(employeeVo.getEmail());
		employee.setGender(employeeVo.getGender().toString());
		employee.setSalary(employeeVo.getSalary());
		employee.setActive(employeeVo.getActive());

		return employee;
	}

}
