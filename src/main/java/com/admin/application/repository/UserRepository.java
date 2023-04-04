package com.admin.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.application.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

	Users findByUserEmail(String userEmail);
	
	
}
