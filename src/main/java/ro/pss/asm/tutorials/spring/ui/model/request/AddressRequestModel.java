package ro.pss.asm.tutorials.spring.ui.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestModel {
	String city;
	String country;
	String street;
	String postalCode;
	String type;
}
