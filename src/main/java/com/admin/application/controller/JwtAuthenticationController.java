package com.admin.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.application.config.AdminCustomerService;
import com.admin.application.config.JwtUtility;
import com.admin.application.dto.JwtRequest;
import com.admin.application.dto.JwtResponse;
import com.admin.application.exceptionhandler.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class JwtAuthenticationController {
	
	@Autowired
	AdminCustomerService customerService;
	
	@Autowired
	private JwtUtility jwtUtility;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/token")
	public ResponseEntity<?>generatetoken(@RequestBody JwtRequest request) throws Exception{
		this.authenticate(request.getEmail(), request.getPassword());
		UserDetails userDetails = this.customerService.loadUserByUsername(request.getEmail());
		String token = this.jwtUtility.generateToken(userDetails);

		JwtResponse response = new JwtResponse();
		response.setToken(token);
	
		return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {

			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			System.out.println("Invalid Detials !!");
			throw new ResourceNotFoundException("Invalid credentials", 404);
		}

	}
}
