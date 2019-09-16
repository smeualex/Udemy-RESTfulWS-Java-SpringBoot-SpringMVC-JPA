package ro.pss.asm.tutorials.spring.ui.model.response;

import java.util.List;

import lombok.Data;

@Data
public class UserRest {
	
	String userId;	// not the DB user id !!! (a public user id, safe to return via rest)
	String firstName;
	String lastName;
	String email;
	List<AddressRest> addresses;
}
