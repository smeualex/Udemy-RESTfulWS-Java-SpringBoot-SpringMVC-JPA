package ro.pss.asm.tutorials.spring.exceptions;

public class AddressServiceException extends RuntimeException{

	private static final long serialVersionUID = -2721396491735289139L;

	public AddressServiceException(String errorMessage) {
		super(errorMessage);
	}
}