package com.admin.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.application.entity.Admin;

public interface AdminRepository  extends JpaRepository<Admin, Integer>{

	Admin findByEmail(String email);
	
	Admin findByAdminId(String adminId);
}
