package com.admin.application.response;

import java.time.LocalDate;
import java.util.List;

import com.admin.application.entity.Course;

import lombok.Data;

@Data
public class UserResponse {
	
	private String traineeId;
	
	private String userName;

	private String userEmail;

	private Long phoneNumber;
	
	private String courseId;
	
	private String trainerName;
	
	private LocalDate joinDate;

}
