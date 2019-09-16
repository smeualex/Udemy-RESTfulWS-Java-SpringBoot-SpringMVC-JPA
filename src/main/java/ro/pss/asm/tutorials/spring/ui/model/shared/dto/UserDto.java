package ro.pss.asm.tutorials.spring.ui.model.shared.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UserDto implements Serializable {

	private static final long serialVersionUID = -8443661839774827344L;

	long id;
	String publicUserId;
	String firstName;
	String lastName;
	String email;
	String password;
	String encryptedPassword;
	String emailVerificationToken;
	Boolean emailVerificationStatus = false;
	List<AddressDto> addresses;
}
