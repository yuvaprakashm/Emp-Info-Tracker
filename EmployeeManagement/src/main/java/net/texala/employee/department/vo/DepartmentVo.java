package net.texala.employee.department.vo;

import java.util.Date;

import lombok.Data;
import net.texala.employee.enums.GenericStatus;

@Data
 
public class DepartmentVo {

	private Long deptId;
	private String deptName;
	private Date createdDate = new Date();
	private GenericStatus status;
}