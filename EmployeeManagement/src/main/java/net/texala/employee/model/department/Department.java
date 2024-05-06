package net.texala.employee.model.department;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "DepartmentId")
	private int deptId;

	@Column(name = "DepartmentName")
	private String deptName;
	
	@Column(name = "Active")
	private Boolean active = false;
}
