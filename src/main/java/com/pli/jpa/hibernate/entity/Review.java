package com.pli.jpa.hibernate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class Review {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String description;
	
	@Enumerated(EnumType.STRING) //default is ordinal value (position related) which is bad in case the definition-position changes later 
	private ReviewRating rating;
	
	//Review-table holds the course id, is the owning side  
	@ManyToOne
	private Course course;

	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	protected Review() {
		
	}
	
	public Review(ReviewRating rating, String description) {
		this.rating = rating;
		this.description = description;
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
	public String getDescription() {
		return description;
	}

	/**
	 * @param name the name to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return String.format("Review [id=%s, description=%s, rating=%s]", id, description, rating);
	}

	/**
	 * @return the rating
	 */
	public ReviewRating getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(ReviewRating rating) {
		this.rating = rating;
	}
	
	

}
