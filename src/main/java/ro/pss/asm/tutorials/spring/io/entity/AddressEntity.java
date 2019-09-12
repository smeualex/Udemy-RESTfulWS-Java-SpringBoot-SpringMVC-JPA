package ro.pss.asm.tutorials.spring.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity(name = "addresses")
public class AddressEntity implements Serializable {

	private static final long serialVersionUID = 6547065631983781706L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	@Column(nullable = false)
	String addressId;
	
	@Column(nullable = false, length = 30)
	String city;
	
	@Column(nullable = false, length = 15)
	String country;
	
	@Column(nullable = false, length = 100)
	String street;
	
	@Column(nullable = false, length = 7)
	String postalCode;
	
	@Column(nullable = false, length = 12)
	String type;
	
	// relation annotation here
	@ManyToOne
	@JoinColumn(name="users_id")
	UserEntity userDetails;

	
	
	public AddressEntity() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserEntity getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserEntity userDetails) {
		this.userDetails = userDetails;
	}
}
