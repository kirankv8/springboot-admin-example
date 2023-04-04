package com.admin.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.application.dto.AdminDto;
import com.admin.application.dto.AdminRequest;
import com.admin.application.dto.OtpEmail;
import com.admin.application.exceptionhandler.ResourceNotFoundException;
import com.admin.application.response.AdminResponse;
import com.admin.application.service.AdminService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/v1")
public class AdminController {

	@Autowired
	private  AdminService adminService;
	
	@PostMapping("/add/admin")
	public AdminResponse saveAdmin(@RequestBody AdminDto adminDto)throws ResourceNotFoundException, MessagingException {
		return adminService.saveAdmin(adminDto);
    } 
	@GetMapping("/genarate/otp/forgotten-password")
	public String OtpGenarate(@RequestBody  OtpEmail adminRequest )throws MessagingException, ResourceNotFoundException{
		return adminService.generateOtp(adminRequest.getEmail());
	}
	
	@PostMapping("/admin/{adminId}/update/password")
	public String updatePassword(@RequestBody AdminRequest dto,@PathVariable String adminId)throws ResourceNotFoundException {
		return adminService.updatePassword(dto,adminId);
	}
	
}
