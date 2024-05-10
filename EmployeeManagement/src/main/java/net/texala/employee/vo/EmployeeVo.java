package net.texala.employee.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.texala.employee.enums.Gender;
import net.texala.employee.enums.GenericStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVo {
	private Long id;
	private String firstName;
	private String lastName;
	private Integer age;
	private String email;
	private Gender gender;
	private Date createdDate = new Date();
	private GenericStatus status;
}
