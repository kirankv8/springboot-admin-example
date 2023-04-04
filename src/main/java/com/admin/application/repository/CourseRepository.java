package com.admin.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.application.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer>{

	List<Course> findByAdminId(String adminId);
	
	Course findByCourseId(String courseId);
	
	Course findByCourseName(String courseName);

}
