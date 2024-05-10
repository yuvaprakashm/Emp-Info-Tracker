package net.texala.employee.address.vo;

import java.util.Date;

import lombok.Data;
import net.texala.employee.enums.GenericStatus;

@Data
public class AddressVo {

	private Long id;
	private String street;
	private String city;
	private String state;
	private String zipcode;
	private Boolean active;
	private Date createdDate = new Date();
	private GenericStatus status;
}
