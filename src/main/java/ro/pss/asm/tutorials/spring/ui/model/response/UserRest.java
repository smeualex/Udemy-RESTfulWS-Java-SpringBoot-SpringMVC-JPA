package ro.pss.asm.tutorials.spring.ui.model.response;

import java.util.List;

public class UserRest {
	
	String userId;	// not the DB user id !!! (a public user id, safe to return via rest)
	String firstName;
	String lastName;
	String email;
	List<AddressRest> addresses;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<AddressRest> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<AddressRest> addresses) {
		this.addresses = addresses;
	}
}
