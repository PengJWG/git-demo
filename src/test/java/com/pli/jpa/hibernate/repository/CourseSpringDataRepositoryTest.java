package com.pli.jpa.hibernate.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.pli.jpa.hibernate.SpringJpaHibernateApplication;
import com.pli.jpa.hibernate.entity.Course;




@SpringBootTest(classes=SpringJpaHibernateApplication.class)
class CourseSpringDataRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CourseSpringDataRepository repository;
	
	
	@Test
	void findById_CoursePresent() {
		Optional<Course> courseOptional = repository.findById(10001L);
		assertTrue(courseOptional.isPresent());
	}
	
	@Test
	void findById_CourseNotPresent() {
		Optional<Course> courseOptional = repository.findById(20001L);
		assertFalse(courseOptional.isPresent());
	}
	
	@Test
	void playAroundWithSpringDataRepository() {
		Course course = new Course("Microservices in 100 Steps");
		repository.save(course);
		
		course.setName("Microservices in 100 Steps - updated");
		repository.save(course);
		
		logger.info("Courses -> {}", repository.findAll());
		
		logger.info("Count -> {}", repository.count());
	}
	
	@Test
	void sort() {
		logger.info("Sorted Courses -> {}", repository.findAll(Sort.by(Sort.Direction.DESC, "name")
				                                                   .and(Sort.by(Sort.Direction.ASC, "lastUpdatedDate"))));
		
		logger.info("Count -> {}", repository.count());
	}
	
	@Test
	void pagination() {
		PageRequest pageRequest = PageRequest.of(0, 3);
		Page<Course> firstPage= repository.findAll(pageRequest);
		logger.info("First Page -> {}", firstPage.getContent());
		
		Pageable secondPageable = firstPage.nextPageable();
		Page<Course> secondPage= repository.findAll(secondPageable);
		logger.info("Second Page -> {}", secondPage.getContent());
	}
	
	@Test
	@Transactional
	void selfDefinedMethods() {
		logger.info("FindByName: Courses -> {}", repository.findByName("Spring in 50 Steps"));
		
		logger.info("CountByName -> {}", repository.countByName("Spring in 50 Steps"));
		
		logger.info("DeleteByName -> {}", repository.deleteByName("Spring in 50 Steps"));
		
		assertEquals(0, repository.findByName("Spring in 50 Steps").size());
		
		logger.info("CourseWith100StepsInNameUsingNamedQuery: Courses -> {}", repository.courseWith100StepsInNameUsingNamedQuery());
	}
}
