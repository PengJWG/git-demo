package com.pli.jpa.hibernate.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;


@Entity
@NamedQueries(value = {
	@NamedQuery(name = "query_get_all_courses", query= "Select c From Course c"),
	@NamedQuery(name = "query_get_all_courses_and_students_join_fetch", 
	            query= "Select c From Course c JOIN FETCH c.students s"), // load course and students data as well
	@NamedQuery(name = "query_get_100_Steps_courses", query= "Select c From Course c Where name like '%100 Steps'")
})

@Cacheable
// when course is deleted, given sql is getting fired automatically to allow soft delete
@SQLDelete(sql = "update course set is_deleted = true where id = ?") 
// retrieve always active courses, ignore deleted courses, this condition is added automatically to each sql call.
// @Where does Not apply to Native Query AND update of is_deleted has NO Impact on update of isDeleted property !!!
// @PreRemove can do additional help
@Where(clause = "is_deleted = false") 
public class Course {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "course")
	private List<Review> reviews = new ArrayList<>();
	
	@ManyToMany(mappedBy = "courses") // make Student the owning side of the relationship
	@JsonIgnore //used for rest call
	private List<Student> students = new ArrayList<>();
	
	@UpdateTimestamp
	private LocalDateTime lastUpdatedDate;
	
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	//use soft delete, Not hard delete
	private boolean isDeleted;

	@PreRemove
	private void preRemove() {
		this.isDeleted = true;
	}
	
	protected Course() {
		
	}
	
	public Course(String name) {
		super();
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		//take attention!! by adding reviews to the method would causes additional DB-access, impact on Performance !!! 
		return String.format("Course [id=%s, name=%s]", id, name);
	}

	/**
	 * @return the reviews
	 */
	public List<Review> getReviews() {
		return reviews;
	}

	/**
	 * @param reviews the reviews to set
	 */
	public void addReview(Review reviews) {
		this.reviews.add(reviews);
	}
	
	public void removeReview(Review reviews) {
		this.reviews.remove(reviews);
	}

	/**
	 * @return the students
	 */
	public List<Student> getStudents() {
		return students;
	}

	/**
	 * @param students the students to set
	 */
	public void addStudent(Student student) {
		this.students.add(student);
	}

}
