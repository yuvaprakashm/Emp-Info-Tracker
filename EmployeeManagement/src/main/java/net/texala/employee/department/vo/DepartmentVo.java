package net.texala.employee.department.vo;

import static net.texala.employee.constants.Constants.*;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import net.texala.employee.enums.GenericStatus;

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
}
