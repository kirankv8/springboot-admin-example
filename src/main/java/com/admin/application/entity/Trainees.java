package com.admin.application.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostPersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trainees {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String traineeId;

	@OneToOne(cascade = CascadeType.ALL)
	private Users users;

	@OneToOne(cascade = CascadeType.ALL)
	private Course courses;

	@PostPersist
	public void setTraineeId() {
		if (id != null) {
			traineeId = "TRN" + 00 + id;
		}
	}
}
