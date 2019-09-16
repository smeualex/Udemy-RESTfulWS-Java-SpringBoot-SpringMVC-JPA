package ro.pss.asm.tutorials.spring.ui.model.shared.dto;

import lombok.Data;

@Data
public class AddressDto {
	long id;
	String addressId;
	
	String city;
	String country;
	String street;
	String postalCode;
	String type;
	UserDto userDetails;	// for bidirectional relationship
}
