package net.texala.employee.model;

import static net.texala.employee.constants.Constants.AGE;
import static net.texala.employee.constants.Constants.CONTACT_NUMBER;
import static net.texala.employee.constants.Constants.CREATED_DATE;
import static net.texala.employee.constants.Constants.DOB;
import static net.texala.employee.constants.Constants.EMAIL;
import static net.texala.employee.constants.Constants.EMPLOYEE_MASTER;
import static net.texala.employee.constants.Constants.FIRST_NAME;
import static net.texala.employee.constants.Constants.GENDER;
import static net.texala.employee.constants.Constants.HIRE_DATE;
import static net.texala.employee.constants.Constants.ID;
import static net.texala.employee.constants.Constants.JOB_TITLE;
import static net.texala.employee.constants.Constants.LAST_NAME;
import static net.texala.employee.constants.Constants.SALARY;
import static net.texala.employee.constants.Constants.STATUS;
import static net.texala.employee.constants.Constants.TIMESTAMP;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import net.texala.employee.address.model.Address;
import net.texala.employee.department.model.Department;
import net.texala.employee.enums.Gender;
import net.texala.employee.enums.GenericStatus;

@Entity
@Getter
@Setter
@Table(name = EMPLOYEE_MASTER)
@Where(clause = "status != 'DELETED'")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = ID)
	private Long id;

	@Column(name = FIRST_NAME,length = 30)
	private String firstName;

	@Column(name = LAST_NAME,length = 30)
	private String lastName;

	@Column(name = AGE)
	private Integer age;

	@Column(name = EMAIL,length = 100)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(name = GENDER)
	private Gender gender;

	@Column(name = SALARY)
	private Integer salary;

	@Enumerated(EnumType.STRING)
	@Column(name = STATUS)
	private GenericStatus status;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@CreatedDate
	@Column(name = CREATED_DATE, nullable = false, updatable = false, columnDefinition = TIMESTAMP)
	private Date createdDate = new Date();

	@Column(name = CONTACT_NUMBER, length = 10)
	private String contactNumber;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = DOB)
	private Date dateOfBirth;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = HIRE_DATE)
	private Date hireDate;

	@Column(name = JOB_TITLE,length = 20)
	private String jobTitle;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Address> addresses;

	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "department_id")
	private Department department;
}