package ro.pss.asm.tutorials.spring.ui.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRest {
	
	String userId;	// not the DB user id !!! (a public user id, safe to return via rest)
	String firstName;
	String lastName;
	String email;
}
