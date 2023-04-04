package com.admin.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.application.entity.Trainer;

public interface TrainerRepository  extends JpaRepository<Trainer, Integer>{

	 Trainer findByTrainerEmail(String trainerEmail);
}
