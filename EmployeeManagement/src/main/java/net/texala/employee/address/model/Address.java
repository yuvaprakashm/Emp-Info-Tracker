package net.texala.employee.address.model;

import static net.texala.employee.constants.Constants.*;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;
import net.texala.employee.enums.AddressType;
import net.texala.employee.enums.GenericStatus;
import net.texala.employee.model.Employee;
import net.texala.employee.vo.EmployeeVo;

@Entity
@Getter
@Setter
@Table(name = ADDRESS_MASTER)
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = ID)
	private Long id;

	@Column(name = STREET)
	private String street;

	@Column(name = CITY)
	private String city;

	@Column(name = STATE)
	private String state;

	@Column(name = ZIPCODE)
	private String zipcode;

	@Enumerated(EnumType.STRING)
	@Column(name = STATUS)
	private GenericStatus status;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@CreatedDate
	@Column(name = CREATED_DATE, nullable = false, updatable = false, columnDefinition = TIMESTAMP)
	private Date createdDate = new Date();

	@Column(name = DOORNUMBER)
	private String doorNumber;

	@Column(name = COUNTRY)
	private String country;

	@Enumerated(EnumType.STRING)
	@Column(name = ADDRESS_TYPE)
	private AddressType addressType;

	@Column(name = LAND_MARK)
	private String landMark;
	
	@ManyToOne
	@JoinColumn(name = "employee_id") 
	private Employee employee;
	

}
