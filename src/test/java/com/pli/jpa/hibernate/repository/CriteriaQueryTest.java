package com.pli.jpa.hibernate.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pli.jpa.hibernate.SpringJpaHibernateApplication;
import com.pli.jpa.hibernate.entity.Course;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@SpringBootTest(classes=SpringJpaHibernateApplication.class)
class CriteriaQueryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CourseRepository repository;
	
	@Autowired
	private EntityManager em;
	
	@Test
	void criteria_all_courses() {
		//"select c from Course c"
		
		//1. Use Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); // return Object Course
		
		//2. Define roots (from-part) for tables which are involved in the query
		Root<Course> courseRoot = cq.from(Course.class);
		
		//3. Define Predicates etc using Criteria Builder, define where-part
		
		//4. Add Predicates etc to the Criteria Query
		
		//5. Build the TypedQuery using the entity manager and criteria query
		 TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
		 
		 List<Course> resultList = query.getResultList();
		 logger.info("criteria_all_courses -> {}", resultList);
	}
	
	@Test
	void criteria_all_courses_having_100Steps() {
		//"select c from Course c where name like '%100 Steps'"
		
		//1. Use Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); // return Object Course
		
		//2. Define roots (from-part) for tables which are involved in the query
		Root<Course> courseRoot = cq.from(Course.class);
		
		//3. Define Predicates etc using Criteria Builder, define where-part
		Predicate like100Steps = cb.like(courseRoot.get("name"), "%100 Steps");
		
		//4. Add Predicates etc to the Criteria Query
		cq.where(like100Steps);
		
		//5. Build the TypedQuery using the entity manager and criteria query
		TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
		 
		List<Course> resultList = query.getResultList();
		logger.info("criteria_all_courses_having_100Steps -> {}", resultList);
	}
	
	@Test
	void criteria_all_courses_without_students() {
		//"select c from Course c where c.students is empty"
		
		//1. Use Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); // return Object Course
		
		//2. Define roots (from-part) for tables which are involved in the query
		Root<Course> courseRoot = cq.from(Course.class);
		
		//3. Define Predicates etc using Criteria Builder, define where-part
		Predicate studentsIsEmpty = cb.isEmpty(courseRoot.get("students"));
		
		//4. Add Predicates etc to the Criteria Query
		cq.where(studentsIsEmpty);
		
		//5. Build the TypedQuery using the entity manager and criteria query
		TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
		 
		List<Course> resultList = query.getResultList();
		logger.info("criteria_all_courses_without_students -> {}", resultList);
	}
	
	@Test
	void join() {
		//"select c from Course c Join c.students s"
		
		//1. Use Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); // return Object Course
		
		//2. Define roots (from-part) for tables which are involved in the query
		Root<Course> courseRoot = cq.from(Course.class);
		
		//3. Define Predicates etc using Criteria Builder, define where-part
		Join<Object, Object> join = courseRoot.join("students");
		//Join<Object, Object> join = courseRoot.join("students", JoinType.LEFT);
		
		//4. Add Predicates etc to the Criteria Query
		
		
		//5. Build the TypedQuery using the entity manager and criteria query
		TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
		 
		List<Course> resultList = query.getResultList();
		logger.info("join -> {}", resultList);
	}
}
