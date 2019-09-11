package ro.pss.asm.tutorials.spring.ui.model.request;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsRequestModel {
	
	String firstName;
	String lastName;
	String email;
	String password;
	List<AddressRequestModel> addresses;
}
