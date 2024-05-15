package net.texala.employee.vo;

import static net.texala.employee.constants.Constants.*;
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
	@NotBlank(message = FIRSTNAME_REQUIRED)
	@Pattern(regexp = "^[a-zA-Z]+$", message = FIRSTNAME_ERROR_MESSAGE)
	private String firstName;
	@NotBlank(message = LASTNAME_REQUIRED)
	@Pattern(regexp = "^[a-zA-Z]+$", message = LASTNAME_ERROR_MESSAGE)
	private String lastName;
	@NotNull(message = AGE_REQUIRED)
	@Min(value = 18, message = MIN_AGE_ERROR_MESSAGE)
	@Max(value = 100, message = MAX_AGE_ERROR_MESSAGE)
	private Integer age;

	@NotBlank(message = EMAIL_REQUIRED)
	@Email(message = EMAIL_VALID_ERROR_MESSAGE)
	private String email;

	@NotBlank(message = GENDER_REQUIRED)
	private Gender gender;
	@NotNull(message = SALARY_REQUIRED)
	@Min(value = 0, message = NON_NEGATIVE_SALARY_ERROR_MESSAGE)
	private Integer salary;

	private Date createdDate = new Date();
	@NotNull(message = STATUS_REQUIRED)
	private GenericStatus status;

}
