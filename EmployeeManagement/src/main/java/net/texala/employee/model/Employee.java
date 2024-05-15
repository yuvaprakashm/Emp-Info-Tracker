package net.texala.employee.model;

import static net.texala.employee.constants.Constants.*;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.texala.employee.enums.Gender;
import net.texala.employee.enums.GenericStatus;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}