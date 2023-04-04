package com.admin.application.dto;

import lombok.Data;

@Data
public class AdminRequest {

	private String email;

	private Integer otp;
	
	private String password;
	
	private String conformPassword;
}
