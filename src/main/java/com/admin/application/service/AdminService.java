package com.admin.application.service;

import com.admin.application.dto.AdminDto;
import com.admin.application.dto.AdminRequest;
import com.admin.application.exceptionhandler.ResourceNotFoundException;
import com.admin.application.response.AdminResponse;

import jakarta.mail.MessagingException;

public interface AdminService  {

	AdminResponse saveAdmin(AdminDto adminDto)throws  ResourceNotFoundException,MessagingException;

	
	String generateOtp(String email)throws ResourceNotFoundException
	, MessagingException ;

	String updatePassword(AdminRequest dto,String adminId) throws ResourceNotFoundException;

}
