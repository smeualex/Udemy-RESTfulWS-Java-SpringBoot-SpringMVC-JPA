package ro.pss.asm.tutorials.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ro.pss.asm.tutorials.spring.exceptions.UserServiceException;
import ro.pss.asm.tutorials.spring.io.entity.UserEntity;
import ro.pss.asm.tutorials.spring.io.repositories.UserRepository;
import ro.pss.asm.tutorials.spring.ui.model.response.ErrorMessages;
import ro.pss.asm.tutorials.spring.ui.model.shared.Utils;
import ro.pss.asm.tutorials.spring.ui.model.shared.dto.AddressDto;
import ro.pss.asm.tutorials.spring.ui.model.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto user) {
		
		if(userRepository.findByEmail(user.getEmail()) != null) {
			throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
		}
		
		// set addresses pubic ids
		for(int i=0; i<user.getAddresses().size();i++) {
			AddressDto addressDto = user.getAddresses().get(i);
			addressDto.setAddressId(utils.generateAddressId(30));
			addressDto.setUserDetails(user);
			user.getAddresses().set(i, addressDto);
		}
		
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);
		
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		// set user public id
		userEntity.setPublicUserId(utils.generateUserId(3));
		
		
		UserEntity storedUserDetails = userRepository.save(userEntity);
		
		UserDto returnUser = modelMapper.map(storedUserDetails, UserDto.class);	
		return returnUser;
	}

	
	@Override
	public UserDto getUser(String email) {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) {
			throw new UsernameNotFoundException(email);
		}
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntity, userDto);
		return userDto;
	}


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// in our case we'll use find by email
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) {
			// this comes also from spring
			throw new UsernameNotFoundException(email);
		}
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}


	@Override
	public UserDto getUserByUserId(String publicUserId) {

		UserEntity userEntity = userRepository.findByPublicUserId(publicUserId);
		if(userEntity == null)
			throw new UsernameNotFoundException(publicUserId);

		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);		
		return userDto;
	}


	@Override
	public UserDto updateUser(String userId, UserDto user) {
		
		UserEntity userEntity = userRepository.findByPublicUserId(userId);
		if(userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		
		UserEntity updateUserDetails = userRepository.save(userEntity);
		UserDto userDto = new ModelMapper().map(updateUserDetails, UserDto.class);
		
		return userDto;
	}


	@Override
	public void deleteUser(String userId) {
		
		UserEntity userEntity = userRepository.findByPublicUserId(userId);
		if(userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userRepository.delete(userEntity);
	}


	@Override
	public Page<UserDto> getUsers(int page, int limit) {
		
		// use page index from 1;
		if(page > 0)
			page-=1;
		
		Pageable pageable= PageRequest.of(page, limit);
		Page<UserEntity> usersPage = userRepository.findAll(pageable);
		List<UserEntity> userEntities = usersPage.getContent();
		// map userEntity list to UserDto list
		java.lang.reflect.Type listType = new TypeToken<List<UserDto>>() {}.getType();
		List<UserDto> userDtos  = new ModelMapper().map(userEntities, listType);
		
		int totalElements = (int) usersPage.getTotalElements();
		
		return new PageImpl<UserDto>(userDtos, pageable, totalElements);
	}

	
}
