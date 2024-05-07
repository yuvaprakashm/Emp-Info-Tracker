package net.texala.employee.vo.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.texala.employee.enums.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVo {
	private int id;
	private String firstName;
	private String lastName;
	private int age;
	private String email;
	private Gender gender;
	private Integer salary;
	private Boolean active;
}
