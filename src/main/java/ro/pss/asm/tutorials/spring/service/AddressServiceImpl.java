package ro.pss.asm.tutorials.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ro.pss.asm.tutorials.spring.io.entity.AddressEntity;
import ro.pss.asm.tutorials.spring.io.entity.UserEntity;
import ro.pss.asm.tutorials.spring.io.repositories.AddressRepository;
import ro.pss.asm.tutorials.spring.io.repositories.UserRepository;
import ro.pss.asm.tutorials.spring.ui.model.response.AddressRest;
import ro.pss.asm.tutorials.spring.ui.model.shared.dto.AddressDto;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Override
	public List<AddressDto> getAddresses(String userId) {
				
		// one method is to get the user by the public id
		// and with the entity fetched from the db, return the addresses
		// get user by public user id
		UserEntity userEntity = userRepository.findByPublicUserId(userId);
		if(userEntity == null) {
			throw new UsernameNotFoundException("User " + userId + " not found!");
		}
		log.error("> addressServiceafter findByPublicUserId ");
		
		// use the AddressRepository and use JPA to query the DB
		Iterable<AddressEntity> addressesEntity = addressRepository.findAllByUserDetails(userEntity);
		// map the found address entities to DTO
		java.lang.reflect.Type listType = new TypeToken<List<AddressDto>>() {}.getType();
		List<AddressDto> addressesDto  = new ModelMapper().map(addressesEntity, listType);
		
		return addressesDto;
	}

	@Override
	public AddressDto getAddress(String addressId) {

		AddressDto addressDto = null;
		
		AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
		if(addressEntity != null) {
			addressDto = new ModelMapper().map(addressEntity, AddressDto.class);
		}
		
		return addressDto;
	}

}
