package ro.pss.asm.tutorials.spring.ui.model.request;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestModel {
	private String email;
	private String password;
}
