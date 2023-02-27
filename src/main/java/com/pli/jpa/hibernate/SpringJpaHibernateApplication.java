package com.pli.jpa.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pli.jpa.hibernate.repository.CourseRepository;
import com.pli.jpa.hibernate.repository.EmployeeRepository;
import com.pli.jpa.hibernate.repository.StudentRepository;

@SpringBootApplication
public class SpringJpaHibernateApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringJpaHibernateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
//		Course course = repository.findById(10001L);
//		logger.info("Course 10001  -> {}", course);
//		courseRepository.save(new Course("Azure in 100 Steps"));
//		courseRepository.playWithEntityManager();
		
		//studentRepository.insertStudentAndCourse();
//		employeeRepository.insert(new PartTimeEmployee("Jill", new BigDecimal(50)));
//		employeeRepository.insert(new FullTimeEmployee("Jack", new BigDecimal(1000)));
//		
//		logger.info("All Employees  -> {}", employeeRepository.retrieveAllEmployees());
	}

}
