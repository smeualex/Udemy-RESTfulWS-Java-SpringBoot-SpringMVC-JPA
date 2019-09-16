package ro.pss.asm.tutorials.spring.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ro.pss.asm.tutorials.spring.service.AddressService;
import ro.pss.asm.tutorials.spring.service.UserService;
import ro.pss.asm.tutorials.spring.ui.model.request.UserDetailsRequestModel;
import ro.pss.asm.tutorials.spring.ui.model.response.AddressRest;
import ro.pss.asm.tutorials.spring.ui.model.response.ErrorMessages;
import ro.pss.asm.tutorials.spring.ui.model.response.OperationStatus;
import ro.pss.asm.tutorials.spring.ui.model.response.UserRest;
import ro.pss.asm.tutorials.spring.ui.model.shared.dto.AddressDto;
import ro.pss.asm.tutorials.spring.ui.model.shared.dto.UserDto;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")	// http://localhost:8080/api/users
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	AddressService addressService;
	
	@GetMapping(path="/{id}",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id) {
		
		UserDto userDto = userService.getUserByUserId(id);
		
		ModelMapper modelMapper= new ModelMapper();
		UserRest returnUser = modelMapper.map(userDto, UserRest.class);
		
		return returnUser;
	}
	
	@GetMapping(
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
			)
	public List<UserRest> getUsers(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit){
		
		List<UserRest> users = new ArrayList<>();
		
		List<UserDto> userDtos = userService.getUsers(page, limit);
		
		ModelMapper modelMapper= new ModelMapper();
		for(UserDto userDto: userDtos) {
			UserRest user = modelMapper.map(userDto, UserRest.class);
			users.add(user);
		}
		return users;
	}
	
	@PostMapping (
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
			)
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails)
		throws Exception{
		
		if(userDetails.getFirstName().isEmpty())
			throw new Exception(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		UserRest userRest = new UserRest();	// user to return
		
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		
		// CALL USER SERVICE TO SAVE THE DTO IN DB
		// created used DTO returned by the user service class
		UserDto createdUser = userService.createUser(userDto);
		// copy created user properties to the return DTO
		userRest = modelMapper.map(createdUser, UserRest.class);
		
		return userRest;
	}
	
	// normally don't include email and password in this flow
	@PutMapping ( path="/{id}",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
			)
	public UserRest updateUser(@RequestBody UserDetailsRequestModel userDetails, @PathVariable String id) {
		
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		
		UserDto updatedUser = userService.updateUser(id, userDto);
		
		UserRest userRest = modelMapper.map(updatedUser, UserRest.class);
		
		return userRest;
	}
	
	@DeleteMapping ( path="/{id}",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE } )
	public OperationStatus deleteUser(@PathVariable String id) {
		
		OperationStatus opStatus = new OperationStatus();
		opStatus.setName(RequestOperation.DELETE.name());
		
		userService.deleteUser(id);
		
		opStatus.setResult(RequestOperationStatus.SUCCESS.name());
		
		return opStatus;
	}
	
	// GET USER'S LIST OF ADDRESSES
	// http://localhost:8080/api/users/ajdk/addresses
	//
	@GetMapping(path="/{id}/addresses",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<AddressRest> getAddresses(@PathVariable String id) {
		
		List<AddressDto> addressesDto = addressService.getAddresses(id);
		java.lang.reflect.Type listType = new TypeToken<List<AddressRest>>() {}.getType();
		List<AddressRest> addresses  = new ModelMapper().map(addressesDto, listType);
		return addresses; 
	}

	// GET USER'S ADDRESS
	// http://localhost:8080/api/users/<userId>/addresses/<addressId>
	//
	@GetMapping(path = "/{userId}/addresses/{addressId}", 
				produces = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE })
	public AddressRest getUserAddress(@PathVariable String userId, @PathVariable String addressId) {

			AddressDto addressesDto = addressService.getAddress(addressId);
	
			Link selfLink = linkTo(methodOn(UserController.class)
					.getUserAddress(userId, addressId))
					.withSelfRel();
			
			Link userLink = linkTo(methodOn(UserController.class)
					.getUser(userId))
					.withRel("user");
			
			Link addressesLink = linkTo(methodOn(UserController.class)
					.getAddresses(userId))
					.withRel("addresses");
			
			AddressRest address = new ModelMapper().map(addressesDto, AddressRest.class);
			
			address.add(selfLink);
			address.add(userLink);
			address.add(addressesLink);
			
			return address;
	}	
}
