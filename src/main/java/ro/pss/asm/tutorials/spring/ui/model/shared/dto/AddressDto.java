package ro.pss.asm.tutorials.spring.ui.model.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
