package com.admin.application.service;

import java.util.List;

import com.admin.application.dto.Courses;
import com.admin.application.entity.Trainees;
import com.admin.application.entity.Trainer;
import com.admin.application.exceptionhandler.ResourceNotFoundException;
import com.admin.application.response.CourseResponse;
import com.admin.application.response.UserResponse;

import jakarta.mail.MessagingException;

public interface CourseService {

	CourseResponse createCourse(Trainer courseDto, String adminId) throws ResourceNotFoundException, MessagingException;

	List<Courses> getAllcourse(String adminId) throws ResourceNotFoundException;

	UserResponse registerUser(String adminId, Trainees trsdto) throws ResourceNotFoundException;

}
