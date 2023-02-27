package com.pli.jpa.hibernate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;


@Entity
public class Passport {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String number;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "passport")
	private Student student;
	
	protected Passport() {
		
	}
	
	public Passport(String number) {
		this.number = number;
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
	public String getNumber() {
		return number;
	}

	/**
	 * @param name the name to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}
	
	@Override
	public String toString() {
		return String.format("Passport [id=%s, number=%s]", id, number);
	}

}
