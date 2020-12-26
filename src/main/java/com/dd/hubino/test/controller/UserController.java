package com.dd.hubino.test.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dd.hubino.test.entity.User;
import com.dd.hubino.test.response.UserDetails;
import com.dd.hubino.test.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.sf.json.JSONObject;

@RestController
@RequestMapping(path="/hubino_test")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/addUser",
        produces = {
            MediaType.APPLICATION_JSON_VALUE
        })
    @ResponseBody
    public ResponseEntity < Object > addUser(@RequestBody User user) {
        logger.info("Entering into the method addUser");
        JSONObject entity = new JSONObject();
        try {
            userService.addUser(user);

            entity.put("success", true);
            entity.put("message", "Successfully User Added");

        } catch (Exception e) {
            logger.error("Error while executing the method addUser " + e.getMessage());
            if(e.getMessage().contains("ConstraintViolationException")) {
                entity.put("failure", true);
                entity.put("message", "Already EmailId Registered! Kindly try login");
            }else {
                entity.put("failure", true);
                entity.put("message", e.getMessage());
            }
        } finally {
            logger.info("Exiting the method addUser");
        }

        return new ResponseEntity < Object > (entity, HttpStatus.OK);


    }
    
    
    @RequestMapping(method = RequestMethod.POST, value = "/loginUser",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
        @ResponseBody
        public ResponseEntity < Object > loginUser(@RequestParam("email_id") String emailId, @RequestParam("password") String password) {
            logger.info("Entering into the method loginUser ");
            JSONObject entity = new JSONObject();
            try {

                List<User> userList = userService.loginUser(emailId,password);
                if (userList.size() != 0) {
            		String token = this.getJWTToken(userList.get(0).getName());
                	entity.put("success", "true");
                    entity.put("message", "User LoggedIn Successfully");
                	entity.put("token", token);
                } else {
                    entity.put("failure", "true");
                    entity.put("message", "Invalid Credentials, Kindly Check!");
                }

            } catch (Exception e) {
                logger.error("Error while executing the method loginUser " + e.getMessage());
                entity.put("failure", true);
                entity.put("message", e.getMessage());

            } finally {
                logger.info("Exiting the method loginUser");
            }

            return new ResponseEntity < Object > (entity, HttpStatus.OK);

        }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateAddress", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
        @ResponseBody
        public ResponseEntity < Object > updateAddress(@RequestParam("ip_address") String ipAddress,@RequestParam("userId") int userId) {
            logger.info("Entering into the method updateAddress ");
            JSONObject entity = new JSONObject();
            try {

                userService.updateAddress(ipAddress,userId);
                entity.put("success", "true");
                entity.put("message", "Successfully Address Updated for User");

            } catch (Exception e) {
                logger.error("Error while executing the method updateAddress " + e.getMessage());
                entity.put("failure", true);
                entity.put("message", e.getMessage());

            } finally {
                logger.info("Exiting the method updateAddress");
            }

            return new ResponseEntity < Object > (entity, HttpStatus.OK);

        }


    @RequestMapping(method = RequestMethod.POST, value = "/listUserDetails",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {
                MediaType.APPLICATION_JSON_VALUE
            })
        @ResponseBody
        public ResponseEntity < Object > listUserDetails(@RequestParam("user_id") int userId) {
            logger.info("Entering into the method listUserDetails ");
            JSONObject entity = new JSONObject();
            UserDetails userResponse = new UserDetails();
            try {

            	userResponse = userService.listUserDetails(userId);
                entity.put("success", "true");
                entity.put("user_details", userResponse);
               
            } catch (Exception e) {
                logger.error("Error while executing the method listUserDetails " + e.getMessage());
                entity.put("failure", true);
                entity.put("message", e.getMessage());

            } finally {
                logger.info("Exiting the method listUserDetails");
            }

            return new ResponseEntity < Object > (entity, HttpStatus.OK);

        }


    
    private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

	


}