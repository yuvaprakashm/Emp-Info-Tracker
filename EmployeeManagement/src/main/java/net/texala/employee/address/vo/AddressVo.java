package net.texala.employee.address.vo;

import static net.texala.employee.constants.Constants.*;
import java.util.Date;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import net.texala.employee.enums.AddressType;
import net.texala.employee.enums.GenericStatus;

@Data
public class AddressVo {

	private Long id;

	@NotBlank(message = STREET_REQUIRED)
	@Size(max = MAX_STREET_LENGTH, message = STREET_LENGTH_ERROR_MESSAGE)
	private String street;

	@NotBlank(message = CITY_REQUIRED)
	@Pattern(regexp = REGEX, message = CITY_LENGTH_ERROR_MESSAGE)
	private String city;

	@NotBlank(message = STATE_REQUIRED)
	@Pattern(regexp = REGEX, message = STATE_LENGTH_ERROR_MESSAGE)
	private String state;

	@NotBlank(message = ZIPCODE_REQUIRED)
	@Pattern(regexp = ZIPCODE_REGEX, message = ZIPCODE_LENGTH_ERROR_MESSAGE)
	private String zipcode;

	@NotNull(message = DATE_REQUIRED)
	private Date createdDate = new Date();

	@NotNull(message = STATUS_REQUIRED)
	private GenericStatus status;

	@NotBlank(message = DOOR_NUMBER_REQUIRED)
	@Size(max = 50, message = DOOR_NUMBER_LENGTH_ERROR_MESSAGE)
	private String doorNumber;

	@NotBlank(message = COUNTRY_REQUIRED)
	@Size(max = 100, message = COUNTRY_LENGTH_ERROR_MESSAGE)
	private String country;

	@Enumerated(EnumType.STRING)

	private AddressType addressType;

	@NotBlank(message = LAND_MARK_REQUIRED)
	@Size(max = 150, message = LAND_MARK_LENGTH_ERROR_MESSAGE)
	private String landMark;
	
}
