package net.texala.employee.model.address;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "AddressId")
	private int addressId;

	@Column(name = "Street")
	private String street;

	@Column(name = "City")
	private String city;

	@Column(name = "State")
	private String state;

	@Column(name = "State")
	private String zipCode;

}
