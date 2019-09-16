package ro.pss.asm.tutorials.spring.ui.model.response;

import lombok.Data;

@Data
public class AddressRest {
	
	String addressId;
	String city;
	String country;
	String street;
	String postalCode;
	String type;
}
