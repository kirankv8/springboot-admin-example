package com.admin.application.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String adminId;

	private String name;

	private String email;

	private Long phoneNumber;

	private LocalDate createOn;

	@Column(nullable=false,unique=true)
	private String password;

	@PostPersist
	public void setAdminId() {
		if (id != null) {
			adminId = "ADM" + 00 + id;
		}
	}

}
