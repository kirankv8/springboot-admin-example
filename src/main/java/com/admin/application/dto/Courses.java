package com.admin.application.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Courses {

	private String courseName;
	
	private String trainerName;
	
	private LocalDate createDate;
	
	private LocalDate expireDate;

	
	
}
