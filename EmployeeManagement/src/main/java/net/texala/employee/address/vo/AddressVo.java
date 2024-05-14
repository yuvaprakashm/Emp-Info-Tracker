package net.texala.employee.address.vo;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import net.texala.employee.enums.GenericStatus;
import static net.texala.employee.constants.Constants.*;

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

	private Date createdDate = new Date();

	@NotNull(message = STATUS_REQUIRED)
	private GenericStatus status;
}
