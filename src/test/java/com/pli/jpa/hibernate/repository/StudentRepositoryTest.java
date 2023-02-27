package com.pli.jpa.hibernate.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.pli.jpa.hibernate.SpringJpaHibernateApplication;
import com.pli.jpa.hibernate.entity.Address;
import com.pli.jpa.hibernate.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


@SpringBootTest(classes=SpringJpaHibernateApplication.class)
class StudentRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StudentRepository repository;
	
	@Autowired
	private EntityManager em;
	
	@Test
	@Transactional(isolation=Isolation.DEFAULT) //EntityManager is the interface to Persistence Context
	//@Transactional from Spring covers transaction across multiple databases & MQ, JMS queues, 
	//is much better than from JPA which covers only 1 database' transaction   
	void retrieveStudentAndPassport() {
		Student student = em.find(Student.class, 20001L);//command finish the transaction if no annotation @Transactional is added.
		logger.info("Student -> {}", student);
		logger.info("Passport -> {}", student.getPassport());
	}
	
	@Test
	@Transactional //EntityManager is the interface to Persistence Context
	void retrieveStudentAndCourses() {
		Student student = em.find(Student.class, 20001L);//command finish the transaction if no annotation @Transactional is added.
		logger.info("Student -> {}", student);
		logger.info("courses -> {}", student.getCourses());
	}
	
	@Test
	@Transactional //EntityManager is the interface to Persistence Context
	void setAddressDetails() {
		Student student = em.find(Student.class, 20001L);
		student.setAddress(new Address("Frankfurt", "Goethestr.", "100"));
		em.flush();
		logger.info("Student -> {}", student);
		logger.info("courses -> {}", student.getCourses());
	}
	
	@Test
	void jpql_students_with_passport_in_certain_pattern() {
		 TypedQuery<Student> typedQuery = em.createQuery("select s from Student s where s.passport.number like '%1234%'", Student.class);
		 List<Student> resultList = typedQuery.getResultList();
		 logger.info("select s from Student s where s.passport.number like '%1234%' -> {}", resultList);
	}
	
}
