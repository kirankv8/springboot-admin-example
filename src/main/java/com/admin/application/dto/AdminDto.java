package com.admin.application.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AdminDto {
	
	private String name;

	private String email;

	private Long phoneNumber;

	private String password;
	
}
