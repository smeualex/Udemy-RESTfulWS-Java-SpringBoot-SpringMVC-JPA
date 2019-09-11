package ro.pss.asm.tutorials.spring.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

	String firstName;
	String lastName;
	String email;
	String password;
}
