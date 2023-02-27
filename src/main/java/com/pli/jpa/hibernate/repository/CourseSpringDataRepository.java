package com.pli.jpa.hibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.pli.jpa.hibernate.entity.Course;

@RepositoryRestResource(path = "courses")
public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {
	
	//find, query, retrieve, delete
	List<Course> findByName(String name);
	
	int countByName(String name);
	
	List<Course> findByNameAndId(String name, Long id);
	
	List<Course> findByNameOrderByIdDesc(String name);
	
	List<Course> deleteByName(String name);
	
	@Query("Select c From Course c Where name like '%100 Steps'")
	List<Course> courseWith100StepsInName();
	
	@Query(value = "Select * From Course Where name like '%100 Steps'",
		   nativeQuery = true)
	List<Course> courseWith100StepsInNameUsingNativeQuery();
	
	@Query(name = "query_get_100_Steps_courses")
	List<Course> courseWith100StepsInNameUsingNamedQuery();
	
}
