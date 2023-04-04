package com.admin.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.application.dto.Courses;
import com.admin.application.entity.Trainees;
import com.admin.application.entity.Trainer;
import com.admin.application.exceptionhandler.ResourceNotFoundException;
import com.admin.application.response.CourseResponse;
import com.admin.application.response.UserResponse;
import com.admin.application.service.CourseService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/v1")
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@PostMapping("admin/{adminId}/create/course")
	public CourseResponse createCourse(@RequestBody Trainer courseDto,@PathVariable String adminId)
			throws ResourceNotFoundException, MessagingException{
		return courseService.createCourse(courseDto,adminId);
	}
	@GetMapping("admin/{adminId}/getAll/courses")
	public List<Courses> getAllcourse(@PathVariable String adminId)throws ResourceNotFoundException{
		return courseService.getAllcourse(adminId);
	}
	@PostMapping("admin/{adminId}/register/user")
	public UserResponse createUser(@PathVariable String adminId ,@RequestBody Trainees trsdto)throws ResourceNotFoundException  {
		return courseService.registerUser(adminId,trsdto);
	}
	
}
