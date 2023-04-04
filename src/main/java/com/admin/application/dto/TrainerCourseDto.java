package com.admin.application.dto;

import java.util.List;

import com.admin.application.entity.Course;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class TrainerCourseDto {

	private String trainerName;

	private String trainerEmail;

	private Long phonenumber;

	private List<Course> courseDtos;
}
