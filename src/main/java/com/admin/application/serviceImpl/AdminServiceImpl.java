package com.admin.application.serviceImpl;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.admin.application.dto.AdminDto;
import com.admin.application.dto.AdminRequest;
import com.admin.application.entity.Admin;
import com.admin.application.exceptionhandler.ResourceNotFoundException;
import com.admin.application.otp.OtpGenarator;
import com.admin.application.repository.AdminRepository;
import com.admin.application.response.AdminResponse;
import com.admin.application.service.AdminService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private OtpGenarator genarator;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public AdminResponse saveAdmin(AdminDto adminDto) throws ResourceNotFoundException, MessagingException {
		if (adminRepository.findByEmail(adminDto.getEmail()) != null) {
			throw new ResourceNotFoundException("email already  exit's in db", 404);
		}

		String encoder = passwordEncoder.encode(adminDto.getPassword());
		adminDto.setPassword(encoder);
		Admin admin = mapper.map(adminDto, Admin.class);
		admin.setCreateOn(LocalDate.now());
		adminRepository.save(admin);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setTo(admin.getEmail());
		mimeMessage.setSubject("ADMIN FLOW GET STARTED"); 
		String content = ("<b>Dear</b> "+ admin.getName()
				+" <br><br>WELCOME TO ADMIN PORTAL , NOW ONWARDS YOU CAN MANAGE YOURS ADMIN FLOW DIGITALLY  <br> <br>"
				+"<br><br><b>Thank you</b> ");
		mimeMessage.setContent(content, "text/html; charset=utf-8");
		mailSender.send(mimeMessage);
		return this.mapper.map(admin, AdminResponse.class);
	}
	@Override
	public String generateOtp(String email) throws ResourceNotFoundException, MessagingException {

		if (adminRepository.findByEmail(email) == null) {
			throw new ResourceNotFoundException("Invalid Email Id ", 404);

		}
		Integer otp = genarator.otp(email);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setTo(email);
		mimeMessage.setSubject("Reset Password Otp");
		String content = ("<b>Dear</b> <br><br>otp for reset ur password is<br> <br>" + otp
				+ "<br><br><b>Thank you</b> ");
		mimeMessage.setContent(content, "text/html; charset=utf-8");
		mailSender.send(mimeMessage);
		return "otp sent your emailId....";
	}

	@Override
	public String updatePassword(AdminRequest dto, String adminId) throws ResourceNotFoundException {
		if (adminRepository.findByEmail(adminId) == null) {
			throw new ResourceNotFoundException("Invalid adminId credentials ", 404);
		}
		if (adminRepository.findByEmail(dto.getEmail()) == null) {
			throw new ResourceNotFoundException("Invalid EmailId ", 404);
		}
		Admin admin = adminRepository.findByEmail(dto.getEmail());
		Integer cacheOtp = genarator.getOTPByKey(dto.getEmail());
		if (cacheOtp != null && cacheOtp.equals(dto.getOtp())) {

			if (dto.getPassword().equals(dto.getConformPassword())) {
				String encoder = passwordEncoder.encode(dto.getPassword());
				admin.setPassword(encoder);
				adminRepository.save(admin);
			}
			else {
				return "password mis-match.....";
			}
			return "password resetted successfully....";
		} else {
			return "Enter a valid OTP";
		}

	}

}
