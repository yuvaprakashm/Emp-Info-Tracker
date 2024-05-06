package net.texala.employee.model.employee;

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
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "EmployeeId")
	private int id;

	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "Age")
	private int age;

	@Column(name = "Email")
	private String email;

	@Column(name = "Gender")
	private String gender;

	@Column(name = "Salary")
	private Integer salary;

	@Column(name = "Active")
	private Boolean active = false;

/*	public Boolean isActive() {

		return getActive();
	}   */
 
}