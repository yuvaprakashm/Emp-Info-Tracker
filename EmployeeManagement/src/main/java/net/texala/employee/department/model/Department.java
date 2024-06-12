package net.texala.employee.department.model;

import static net.texala.employee.constants.Constants.*;
import java.math.BigDecimal;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import net.texala.employee.enums.GenericStatus;
import net.texala.employee.model.Employee;

@Entity
@Getter
@Setter
@Table(name = DEPARTMENT_MASTER)
@Where(clause = "status != 'DELETED'")
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = DEPT_ID)
	private Long deptId;

	@Column(name = DEPT_NAME,length = 20)
	private String deptName;

	@Enumerated(EnumType.STRING)
	@Column(name = STATUS)
	private GenericStatus status;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@CreatedDate
	@Column(name = CREATED_DATE, nullable = false, updatable = false, columnDefinition = TIMESTAMP)
	private Date createdDate = new Date();

	@Column(name = DEPT_CONTACT_NUMBER,length = 10)
	private String deptContactNumber;

	@Column(name = EMAIL_ADDRESS,length = 30)
	private String emailAddress;

	@Column(name = BUDGET)
	private BigDecimal budget;

	@OneToMany(mappedBy = "department",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonIgnore
	private List<Employee> employees;

}
