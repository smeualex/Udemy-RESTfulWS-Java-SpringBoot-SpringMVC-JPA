package ro.pss.asm.tutorials.spring.io.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = -3122780025763696322L;

	@Id
	@GeneratedValue
	private long id; // db user id

	@Column(nullable = false)
	private String publicUserId; // string user id for public use

	@Column(nullable = false, length = 50)
	private String firstName;

	@Column(nullable = false, length = 50)
	private String lastName;

	@Column(nullable = false, length = 120, unique = true)
	String email;

	@Column(nullable = false)
	private String encryptedPassword;

	private String emailVerificationToken;

	// @Column(nullable=false, columnDefinition = "boolean default false")
	// columnDefinition = "boolean default false" not portable across different dbs
	@Column(nullable = false)
	private Boolean emailVerificationStatus = false;
	
	// one to many mapping
	// 		mappedBy -> name of the object which is mapping this
	//      cascade  -> ALL ( propagate delete \ update addresses to deletion of the user)
	@OneToMany(
			mappedBy = "userDetails", 
			cascade = CascadeType.ALL)
	List<AddressEntity> addresses;
}
