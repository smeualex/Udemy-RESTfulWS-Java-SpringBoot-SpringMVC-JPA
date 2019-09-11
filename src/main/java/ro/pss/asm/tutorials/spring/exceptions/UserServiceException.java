package ro.pss.asm.tutorials.spring.exceptions;

public class UserServiceException extends RuntimeException{

	private static final long serialVersionUID = -9174279781461864671L;

	public UserServiceException(String errorMessage) {
		super(errorMessage);
	}
}
