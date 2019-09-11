package ro.pss.asm.tutorials.spring.service;

import java.util.List;

import ro.pss.asm.tutorials.spring.ui.model.shared.dto.AddressDto;

public interface AddressService {

	List<AddressDto> getAddresses(String userId);
	AddressDto getAddress(String addressId);
}
