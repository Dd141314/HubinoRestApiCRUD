package com.dd.hubino.test.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dd.hubino.test.Dao.UserDao;
import com.dd.hubino.test.entity.Address;
import com.dd.hubino.test.entity.User;
import com.dd.hubino.test.response.UserDetails;

@Service
@SuppressWarnings("unchecked")
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDao userDao;
    
	RestTemplate restTemplate = new RestTemplate();

    public void addUser(User user) throws Exception{
        logger.info("Entering Method addUser");
        try {
            userDao.addUser(user);
        } catch (Exception e) {
            logger.error("Error While executing the addUser " + e.getMessage());
            throw e;
        } finally {
            logger.info("Exiting the method addUser");
        }
    }
    
	public List<User> loginUser(String emailId, String password)  throws Exception{
		logger.info("Entering Method loginUser");
		try {
			return userDao.loginUser(emailId,password);
		}catch(Exception e){
			logger.error("Error While executing the loginUser "+ e.getMessage());
			throw e;
		}finally {
			logger.info("Exiting the method loginUser");
		}
		
	}
	
	public void updateAddress(String ipAddress, int userId) throws Exception {
		logger.info("Entering Method updateAddress");
		try {
			final String URL = "http://api.ipstack.com/{ipAddress}?access_key=626053333c5203dcd83c391d63485afb";
			Map<String, String> reqParam = new HashMap<String,String>();
			reqParam.put("ipAddress", ipAddress);
			Map<String, String> externalAPIResponse = restTemplate.getForObject(URL, HashMap.class,reqParam);
			Address addressObj = new Address();
			addressObj.setUserId(userId);
			addressObj.setCity(externalAPIResponse.get("city"));
			addressObj.setState(externalAPIResponse.get("region_name"));
			addressObj.setCountry(externalAPIResponse.get("country_name"));
			userDao.updateAddress(addressObj);
		}catch(Exception e){
			logger.error("Error While executing the updateAddress "+ e.getMessage());
			throw e;
		}finally {
			logger.info("Exiting the method updateAddress");
		}
	}

	public UserDetails listUserDetails(int userId) {
		logger.info("Entering Method listUserDetails");
		try {
			return userDao.listUserDetails(userId);
		}catch(Exception e){
			logger.error("Error While executing the listUserDetails "+ e.getMessage());
			throw e;
		}finally {
			logger.info("Exiting the method listUserDetails");
		}
		
	}






}