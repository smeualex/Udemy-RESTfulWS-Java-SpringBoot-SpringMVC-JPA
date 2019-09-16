package ro.pss.asm.tutorials.spring.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;


@Data
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
}
