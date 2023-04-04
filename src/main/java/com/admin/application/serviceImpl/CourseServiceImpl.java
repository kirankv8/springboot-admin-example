package com.admin.application.serviceImpl;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.admin.application.dto.Courses;
import com.admin.application.entity.Course;
import com.admin.application.entity.Trainees;
import com.admin.application.entity.Trainer;
import com.admin.application.entity.Users;
import com.admin.application.exceptionhandler.ResourceNotFoundException;
import com.admin.application.repository.AdminRepository;
import com.admin.application.repository.CourseRepository;
import com.admin.application.repository.TraineesRepository;
import com.admin.application.repository.TrainerRepository;
import com.admin.application.repository.UserRepository;
import com.admin.application.response.CourseResponse;
import com.admin.application.response.UserResponse;
import com.admin.application.service.CourseService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private TrainerRepository trainerRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public ModelMapper mapper;

	@Autowired
	private TraineesRepository traineesRepository;

	@Override
	public CourseResponse createCourse(Trainer courseDto, String adminId)
			throws ResourceNotFoundException, MessagingException {
		if (adminRepository.findByAdminId(adminId) == null) {
			throw new ResourceNotFoundException("Invalid adminId ", 404);
		}
		Trainer trainer = trainerRepository.findByTrainerEmail(courseDto.getTrainerEmail());
		List<Course> courses = new ArrayList<>();
		CourseResponse courseResponse = new CourseResponse();
		if (trainer == null) {
			Trainer trn = mapper.map(courseDto, Trainer.class);
			trainerRepository.save(trn);

			for (Course course : courseDto.getCourses()) {
				course.setTrainer(trn);
				course.setAdminId(adminId);
				course.setCreateDate(LocalDate.now());
				course.setExpireDate(LocalDate.now().plusYears(1));
				Duration duration = Duration.between(course.getCreateDate().atStartOfDay(),
						course.getExpireDate().atStartOfDay());
				course.setDuration(duration.toDays());

				courseRepository.save(course);
				courses.add(course);

				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
				helper.setTo(courseDto.getTrainerEmail());
				mimeMessage.setSubject("NEW COURSE ALLOTMENT");
				String content = ("Dear <b> " + courseDto.getTrainerName()
						+ "</b><br><br>we are assigin to new training on <b> " + course.getCourseName()
						+ "</b><br>validity till <b>" + course.getDuration() + "<b><br><br><b>Thank you</b> ");
				mimeMessage.setContent(content, "text/html; charset=utf-8");
				mailSender.send(mimeMessage);

			}

		} else {

			for (Course course : courseDto.getCourses()) {
				course.setAdminId(adminId);
				course.setCreateDate(LocalDate.now());
				course.setExpireDate(LocalDate.now().plusYears(1));
				Duration duration = Duration.between(course.getCreateDate().atStartOfDay(),
						course.getExpireDate().atStartOfDay());
				course.setDuration(duration.toDays());
				course.setTrainer(trainer);
				courseRepository.save(course);
				courses.add(course);

				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
				helper.setTo(courseDto.getTrainerEmail());
				mimeMessage.setSubject("NEW COURSE ALLOTMENT");
				String content = ("Dear <b> " + courseDto.getTrainerName()
						+ "</b><br><br>we are assigin to new training on <b> " + course.getCourseName()
						+ "</b><br>validity till <b> " + course.getDuration() + " days <b><br><br><b>Thank you</b> ");
				mimeMessage.setContent(content, "text/html; charset=utf-8");
				mailSender.send(mimeMessage);

			}

		}
		courseResponse.setTrainerName(courseDto.getTrainerName());
		courseResponse.setTrainerEmail(courseDto.getTrainerEmail());
		courseResponse.setPhonenumber(courseDto.getPhonenumber());
		courseResponse.setCourses(courses);
		return courseResponse;
	}

	@Override
	public List<Courses> getAllcourse(String adminId) throws ResourceNotFoundException {
		if (adminRepository.findByAdminId(adminId) == null) {
			throw new ResourceNotFoundException("Invalid adminId ", 404);
		}
		List<Course> course = courseRepository.findByAdminId(adminId);
		List<Courses> csq1 = new ArrayList<>();
		for (Course cos : course) {
			Courses courses = mapper.map(cos, Courses.class);
			courses.setTrainerName(cos.getTrainer().getTrainerName());
			csq1.add(courses);
		}
		return csq1;
	}

	@Override
	public UserResponse registerUser(String adminId, Trainees trsdto) throws ResourceNotFoundException {
		if (adminRepository.findByAdminId(adminId) == null) {
			throw new ResourceNotFoundException("Invalid adminId ", 404);
		}
		if(courseRepository.findByCourseId(trsdto.getCourses().getCourseId())==null) {
			throw new ResourceNotFoundException("Invalid courseId ", 404);
		}
		Users user = userRepository.findByUserEmail(trsdto.getUsers().getUserEmail());
		Course course = courseRepository.findByCourseId(trsdto.getCourses().getCourseId());
		if (user == null) {
			trsdto.getUsers().setJoinDate(LocalDate.now());
			userRepository.save(trsdto.getUsers());
			trsdto.setUsers(trsdto.getUsers());
			trsdto.setCourses(course);
			traineesRepository.save(trsdto);
		} else {
			trsdto.setUsers(user);
			trsdto.setCourses(course);
			traineesRepository.save(trsdto);
		}
		UserResponse response = mapper.map(trsdto.getUsers(), UserResponse.class);
		response.setTraineeId(trsdto.getTraineeId());
		response.setCourseId(course.getCourseId());
		response.setTrainerName(course.getTrainer().getTrainerName());
		return response;
	}
}
