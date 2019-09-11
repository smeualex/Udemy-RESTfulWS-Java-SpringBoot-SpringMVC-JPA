package ro.pss.asm.tutorials.spring.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.pss.asm.tutorials.spring.ui.model.shared.dto.UserDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "addresses")
public class AddressEntity implements Serializable {
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = -4361278622515522493L;

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
