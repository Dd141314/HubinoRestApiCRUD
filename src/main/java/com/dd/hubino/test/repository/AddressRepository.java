package com.dd.hubino.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dd.hubino.test.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{

	List<Address> findAllById(Integer integer);
	


}
