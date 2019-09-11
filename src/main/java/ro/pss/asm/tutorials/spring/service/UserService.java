package ro.pss.asm.tutorials.spring.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import ro.pss.asm.tutorials.spring.ui.model.shared.dto.UserDto;

public interface UserService extends UserDetailsService{
	
	UserDto createUser(UserDto user);
	UserDto getUser(String email);
	UserDto getUserByUserId(String publicUserId);
	UserDto updateUser(String userId, UserDto user);
	void deleteUser(String userId);
	List<UserDto> getUsers(int page, int limit);
}
