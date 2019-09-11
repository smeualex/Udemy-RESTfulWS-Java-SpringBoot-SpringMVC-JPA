package ro.pss.asm.tutorials.spring.ui.model.shared.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 123669826084735191L;
	
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
