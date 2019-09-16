package ro.pss.asm.tutorials.spring.ui.model.response;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;

@Data
public class AddressRest extends ResourceSupport  {
	
	String addressId;
	String city;
	String country;
	String street;
	String postalCode;
	String type;
}
