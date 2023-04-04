package com.admin.application.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AdminResponse {

	private String adminId;
	
	private String name;

	private String email;

	private Long phoneNumber;

	private LocalDate createOn;

}
