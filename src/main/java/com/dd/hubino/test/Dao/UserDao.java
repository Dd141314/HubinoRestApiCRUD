package com.dd.hubino.test.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dd.hubino.test.entity.Address;
import com.dd.hubino.test.entity.Department;
import com.dd.hubino.test.entity.Role;
import com.dd.hubino.test.entity.User;
import com.dd.hubino.test.repository.AddressRepository;
import com.dd.hubino.test.repository.DepartmentRepository;
import com.dd.hubino.test.repository.RoleRepository;
import com.dd.hubino.test.repository.UserRepository;
import com.dd.hubino.test.response.UserDetails;

@Repository
public class UserDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;



	
	public void addUser(User user) throws Exception {
        logger.info("Entering Method addUser ");
        try {
        	if(user.getId() <= 0) {
    			userRepository.save(user);
        	}
        } catch (Exception re) {
        	logger.error(" Error while executing the method addUser " + re.getMessage() + re.getClass());
            throw re;
        } finally {
        	logger.info("Exiting the method addUser");

        }

	}
	
	
	public List<User> loginUser(String emailId, String password) throws Exception {
        logger.info("Entering Method loginUser ");
        List<User> userList = new ArrayList<User> ();
        try {
        	userList = userRepository.findALL(emailId,password);
        	
        } catch (Exception re) {
        	logger.error(" Error while executing the method loginUser " + re.getMessage() + re.getClass());
            throw re;
        } finally {
        	logger.info("Exiting the method loginUser");

        }
		return userList;

	}
	
	
	public void updateAddress(Address address) throws Exception {
        logger.info("Entering Method updateAddress ");
        try {
        	if(address.getId() <= 0) {
        		addressRepository.save(address);
        	}
        } catch (Exception re) {
        	logger.error(" Error while executing the method updateAddress " + re.getMessage() + re.getClass());
            throw re;
        } finally {
        	logger.info("Exiting the method updateAddress");

        }

	}


	@SuppressWarnings("deprecation")
	public UserDetails listUserDetails(int userId) {
        logger.info("Entering Method loginUser ");
		Optional<User> userList = null;
		Optional<Role> roleList = null;
		Optional<Department> departmentList = null;
		List<Address> addressList = new ArrayList<Address>();
		
		UserDetails userDetails = new UserDetails();
        try {
        	 userList = userRepository.findById(new Integer(userId));
        	 roleList = roleRepository.findById(new Integer(userList.get().getRole_id()));
        	 departmentList = departmentRepository.findById(new Integer(userList.get().getDepartment_id()));
        	 addressList = addressRepository.findAllById(new Integer(userId));
        	 userDetails.setName(userList.get().getName());
        	 userDetails.setRole(roleList.get().getRoleName());
        	 userDetails.setDeptName(departmentList.get().getDeptName());
        	 userDetails.setAddress(addressList);
        	 
        } catch (Exception re) {
        	logger.error(" Error while executing the method loginUser " + re.getMessage() + re.getClass());
            throw re;
        } finally {
        	logger.info("Exiting the method loginUser");

        }
		return userDetails;
	}


}
