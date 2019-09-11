package ro.pss.asm.tutorials.spring.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import ro.pss.asm.tutorials.spring.service.UserService;
import ro.pss.asm.tutorials.spring.ui.model.request.UserDetailsRequestModel;
import ro.pss.asm.tutorials.spring.ui.model.response.ErrorMessages;
import ro.pss.asm.tutorials.spring.ui.model.response.OperationStatus;
import ro.pss.asm.tutorials.spring.ui.model.response.UserRest;
import ro.pss.asm.tutorials.spring.ui.model.shared.dto.UserDto;

@RestController
@RequestMapping("/api/users")	// http://localhost:8080/api/users
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping(path="/{id}",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id) {
		
		UserRest returnUser = new UserRest();
		
		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnUser);
		
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
		
		for(UserDto userDto: userDtos) {
			UserRest user = new UserRest();
			BeanUtils.copyProperties(userDto, user);
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
		
		UserRest userRest = new UserRest();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto updatedUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updatedUser, userRest);
		
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
}
