package net.texala.employee.department.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentVo {

	private int deptId;
	private String deptName;
	private Boolean active = false;
}
