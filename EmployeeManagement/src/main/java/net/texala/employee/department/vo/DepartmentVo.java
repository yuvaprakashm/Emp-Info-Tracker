package net.texala.employee.department.vo;

import static net.texala.employee.constants.Constants.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import net.texala.employee.enums.GenericStatus;
import net.texala.employee.vo.EmployeeVo;

@Data
public class DepartmentVo {

	private Long deptId;

	@NotBlank(message = DEPARTMENT_NAME_REQUIRED)
	@Size(min = 5, max = 50, message = DEPARTMENT_LENGTH_ERROR_MESSAGE)
	private String deptName;

	@NotNull(message = DATE_REQUIRED)
	private Date createdDate = new Date();

	@NotNull(message = STATUS_REQUIRED)
	private GenericStatus status;
	
	@NotBlank(message = DEPT_CONTACT_NUMBER_REQUIRED)
	@Pattern(regexp = "^\\d{10}$", message = DEPT_CONTACT_NUMBER_FORMAT_ERROR_MESSAGE)
	private String deptContactNumber;
	
	@NotBlank(message = EMAIL_REQUIRED)
	@Email(message = EMAIL_VALID_ERROR_MESSAGE)
	private String emailAddress;

	@NotNull(message = BUDGET_REQUIRED)
	@DecimalMin(value = "0.0", inclusive = false, message = BUDGET_MIN_VALUE_ERROR_MESSAGE)
	private BigDecimal budget;

	private List<EmployeeVo> employees;

}
