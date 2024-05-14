package net.texala.employee.department.vo;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import net.texala.employee.enums.GenericStatus;

@Data
public class DepartmentVo {

	private Long deptId;

	@NotBlank(message = "Department name is required")
	@Size(min = 5, max = 50, message = "Department name must be between 5 and 50 characters")
	private String deptName;

	@NotNull(message = "Created date is required")
	private Date createdDate = new Date();

	@NotNull(message = "Status is required")
	private GenericStatus status;
}
