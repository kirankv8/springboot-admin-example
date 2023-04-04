package com.admin.application.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostPersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//to ignore the cycling mapping
@JsonIgnoreProperties(value = { "users" })
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String courseName;

	private LocalDate createDate;
	
	private LocalDate expireDate;
	
	private Long duration;

	private String description;
	
	private String courseId;

	private String adminId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "trainer_id")
	@JsonBackReference
	private Trainer trainer;
	
	@PostPersist
	public void setCourseId() {
		courseId="CUR"+00+id;
	}
}

