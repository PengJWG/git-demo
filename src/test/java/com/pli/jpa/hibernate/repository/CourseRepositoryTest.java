package com.pli.jpa.hibernate.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.pli.jpa.hibernate.SpringJpaHibernateApplication;
import com.pli.jpa.hibernate.entity.Course;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


@SpringBootTest(classes=SpringJpaHibernateApplication.class)
class CourseRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CourseRepository repository;
	
	@Autowired
	private EntityManager em;
	
	@Test
	void jpql_basic() {
		 TypedQuery query = em.createQuery("select c from Course c", Course.class);
		 List<Course> resultList = query.getResultList();
		 logger.info("TypedQuery select c from Course c -> {}", resultList);
	}
	
	@Test
	@Transactional //annotation turn on first level cache on this method boundary, 
	// without it DB will be accessed twice, each on findById-method transaction boundary
	// use transactional boundary on service-layer instead of data-layer for first level cache 
	void findById_firstLevelCacheDemo() {
		 Course course = repository.findById(10001L);
		 logger.info("First Course Retrieved {}", course);
		 assertEquals("JPA in 50 Steps", course.getName());
		 
		 Course course1 = repository.findById(10001L);
		 logger.info("First Course Retrieved again {}", course1);
		 assertEquals("JPA in 50 Steps", course1.getName());
	}
	
}
