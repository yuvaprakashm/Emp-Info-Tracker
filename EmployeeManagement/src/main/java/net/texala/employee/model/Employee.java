package net.texala.employee.model;

import static net.texala.employee.constants.Constants.*;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name =  EMPLOYEE_MASTER)
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = ID)
	private Long id;

	@Column(name = FIRST_NAME)
	private String firstName;

	@Column(name = LAST_NAME)
	private String lastName;

	@Column(name = AGE)
	private Integer age;

	@Column(name = EMAIL)
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

	@Column(name = CONTACT_NUMBER)
	private String contactNumber;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = DOB)
	private Date dateOfBirth;                                   

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = HIRE_DATE)
	private Date hireDate;

	@Column(name = JOB_TITLE)
	private String jobTitle;
	

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Address> addresses;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id")
    private Department department;


 
}