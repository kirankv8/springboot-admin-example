package com.admin.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.admin.application.entity.Admin;
import com.admin.application.repository.AdminRepository;

@Service
public class AdminCustomerService implements UserDetailsService{
	
	@Autowired
	private AdminRepository adminRepository;
	

	@Override
	public CustomUserService loadUserByUsername(String email) throws UsernameNotFoundException {
		if(adminRepository.findByEmail(email)==null){
			
			throw new UsernameNotFoundException("Invalid Email Id ");
			
			}
		
		Admin admin=adminRepository.findByEmail(email);
		CustomUserService service=new CustomUserService();
		service.setUser(admin);
		return service;
    }
}	
