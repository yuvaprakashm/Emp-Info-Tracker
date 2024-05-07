package net.texala.employee.vo.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressVo {
	private int addressId;
	private String street;
	private String city;
	private String state;
	private Long zipcode;
	private Boolean active = false;
	
}
