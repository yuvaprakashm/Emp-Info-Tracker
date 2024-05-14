package net.texala.employee.vo;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
	@NotBlank(message = "FirstName is Required")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "firstName must contain only alphabetic characters")
	private String firstName;
	@NotBlank(message = "LastName is Required")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "lastname must contain only alphabetic characters")
	private String lastName;
	@NotNull(message = "Age is Required")
	@Min(value = 18, message = "Age must be at least 18")
	@Max(value = 100, message = "Age must be at most 100")
	private Integer age;

	@NotBlank(message = "Email is Required")
	@Email(message = "Email must be valid")
	private String email;

	@NotBlank(message = "Gender is Required")
	private Gender gender;
	@NotNull(message = "Salary is Required")
	@Min(value = 0, message = "Salary must be non-negative")
	private Integer salary;

	private Date createdDate = new Date();
	@NotNull(message = "Status is Required")
	private GenericStatus status;

}
