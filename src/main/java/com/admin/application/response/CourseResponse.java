package com.admin.application.response;

import java.util.List;
import com.admin.application.entity.Course;
import lombok.Data;

@Data
public class CourseResponse {

	private String trainerName;

	private String trainerEmail;

	private Long phonenumber;

	private List<Course> courses;

}