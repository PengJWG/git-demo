package com.pli.jpa.hibernate.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.pli.jpa.hibernate.SpringJpaHibernateApplication;
import com.pli.jpa.hibernate.entity.Course;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Subgraph;


@SpringBootTest(classes=SpringJpaHibernateApplication.class)
class PerformanceTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private EntityManager em;
	
	
	@Test
	@Transactional //annotation turn on first level cache on this method boundary, 
	// without it DB will be accessed twice, each on findById-method transaction boundary
	// use transactional boundary on service-layer instead of data-layer for first level cache 
	void creatingNPlusOneProblem() {
		// issue: for each found course separate DB-access is required to get related students
		// -> N + 1 Problem, performance issue
		List<Course> courses = em.createNamedQuery("query_get_all_courses", Course.class).getResultList();
		for (Course course : courses) {
			logger.info("Course -> {}, Students -> {}", course, course.getStudents());
		}
	}
	
	@Test
	@Transactional
	void solvingNPlusOneProblem_EntityGraph() {
		EntityGraph<Course> entityGraph = em.createEntityGraph(Course.class);
		Subgraph<Object> subgraph = entityGraph.addSubgraph("students");
		// load course and students as well at same time
		List<Course> courses = em.createNamedQuery("query_get_all_courses", Course.class)
				                 .setHint("javax.persistence.loadgraph", entityGraph)
				                 .getResultList();
		 
		for (Course course : courses) {
			logger.info("Course -> {}, Students -> {}", course, course.getStudents());
		}
	}
	
	@Test
	@Transactional
	void solvingNPlusOneProblem_JoinFetch() {
		// load course and students as well at same time
		List<Course> courses = em.createNamedQuery("query_get_all_courses_and_students_join_fetch", Course.class)
				                 .getResultList();
		 
		for (Course course : courses) {
			logger.info("Course -> {}, Students -> {}", course, course.getStudents());
		}
	}
}
