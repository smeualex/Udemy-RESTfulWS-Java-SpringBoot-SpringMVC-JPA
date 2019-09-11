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

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = -8831327771366459639L;

	@Id
	@GeneratedValue
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
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
	// columnDefinition = "boolean default false" not portable accross different dbs
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
