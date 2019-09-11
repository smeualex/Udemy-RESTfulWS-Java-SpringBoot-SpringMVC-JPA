package ro.pss.asm.tutorials.spring.io.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ro.pss.asm.tutorials.spring.io.entity.AddressEntity;
import ro.pss.asm.tutorials.spring.io.entity.UserEntity;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long>{
	List<AddressEntity> findAllByUserDetails(UserEntity userEntity);
	AddressEntity findByAddressId(String addressId);
}

