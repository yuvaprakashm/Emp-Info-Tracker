package net.texala.employee.model.department;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "DepartmentId")
	private int deptId;

	@Column(name = "DepartmentName")
	private String deptName;

}
