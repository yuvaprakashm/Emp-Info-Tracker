package net.texala.employee.address.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;
import net.texala.employee.enums.GenericStatus;
import static net.texala.employee.constants.Constants.*;

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
	@Column(name = "created_date", nullable = false, updatable = false, columnDefinition = TIMESTAMP)
	private Date createdDate = new Date();
}
