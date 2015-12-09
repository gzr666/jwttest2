package com.example.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.RoleModel;
import com.example.dao.UserLogin;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.exception.BookNotFoundException;
import com.example.helpers.ModelFactory;
import com.example.repos.RoleRepository;
import com.example.repos.UserRepository;


@RestController
public class UserController {
	
	 private final Map<String, List<String>> userDb = new HashMap<>();
	 
	 private UserRepository userRepository;
	 
	 private RoleRepository roleRepository;

	 @Autowired
	 public UserController(UserRepository userRepository,RoleRepository roleRepository ) {
		 
		 this.userRepository = userRepository;
		 this.roleRepository = roleRepository;
		 	
	        userDb.put("geezer", Arrays.asList("user"));
	        userDb.put("sally", Arrays.asList("user", "admin"));
	    }
	 
	 @RequestMapping(value = "api/login", method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	    public LoginResponse login(@RequestBody final UserLogin login)
	         {
		 
		 	//User user = userRepository.findByUsername(login.username);
		 	User user = userRepository.findByUsername(login.username);
		 
	        if (user==null) {
	            throw new BookNotFoundException("Error");
	        }
	        else if (!BCrypt.checkpw(login.password, user.getPassword())) {
				
	        	throw new BookNotFoundException("wrong password");
			}
	        
	        List<RoleModel> roleModels = ModelFactory.Create(user.getRoles());
	        return new LoginResponse(Jwts.builder().setSubject(user.getUsername())
	            .claim("roles", roleModels)
	            .claim("testing", "tester")
	            .setIssuedAt(new Date())
	            .signWith(SignatureAlgorithm.HS256, "secretkey").compact());
	    }
	 
	 
	 	@RequestMapping(value="api/register",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	 	public ResponseEntity<String> register(@RequestBody UserLogin userLogin)
	 	{
	 		
	 		User user = new User();
	 		Role role = roleRepository.findByName("user");
	 		List<Role> userroles = new ArrayList<Role>();
	 		userroles.add(role);
	 		
	 		user.setUsername(userLogin.getUsername());
	 		user.setPassword(BCrypt.hashpw(userLogin.getPassword(),BCrypt.gensalt()));
	 		user.setRoles(userroles);
	 		
	 		userRepository.save(user);
	 		
	 		return new ResponseEntity<String>("Succesful registration",HttpStatus.OK);
	 		
	 		
	 		
	 		
	 	}
	 
	 
	 

	    @SuppressWarnings("unused")
	    private static class LoginResponse {
	        public String token;

	        public LoginResponse(final String token) {
	            this.token = token;
	        }
	    }

}
