package ro.pss.asm.tutorials.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ro.pss.asm.tutorials.spring.io.entity.AddressEntity;
import ro.pss.asm.tutorials.spring.io.entity.UserEntity;
import ro.pss.asm.tutorials.spring.io.repositories.AddressRepository;
import ro.pss.asm.tutorials.spring.io.repositories.UserRepository;
import ro.pss.asm.tutorials.spring.ui.model.shared.dto.AddressDto;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Override
	public List<AddressDto> getAddresses(String userId) {
		
		List<AddressDto> addressesDto = new ArrayList<>();
		
		// one method is to get the user by the public id
		// and with the entity fetched from the db, return the addresses
		// get user by public user id
		UserEntity userEntity = userRepository.findByPublicUserId(userId);
		if(userEntity == null) {
			throw new UsernameNotFoundException("User " + userId + " not found!");
		}
		
		// use the AddressRepository and use JPA to query the DB
		Iterable<AddressEntity> addressesEntity = addressRepository.findAllByUserDetails(userEntity);
		
		// mapp each addressEntity to addressDto
		ModelMapper modelMapper = new ModelMapper();
		for(AddressEntity addressEntity: addressesEntity) {
			addressesDto.add(
						modelMapper.map(addressEntity, AddressDto.class)
					);
		}
		
		return addressesDto;
	}

}
