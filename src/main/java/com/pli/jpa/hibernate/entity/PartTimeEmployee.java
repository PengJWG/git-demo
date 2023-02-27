package com.pli.jpa.hibernate.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;


@Entity
public class PartTimeEmployee extends Employee {
	
	private BigDecimal hourlyWage;
	
	protected PartTimeEmployee() {
		
	}
	
	public PartTimeEmployee(String name, BigDecimal hourlyWage) {
		super(name);
		this.hourlyWage = hourlyWage;
	}
	
	/**
	 * @return the hourlyWage
	 */
	public BigDecimal getHourlyWage() {
		return hourlyWage;
	}

	/**
	 * @param hourlyWage the hourlyWage to set
	 */
	public void setHourlyWage(BigDecimal hourlyWage) {
		this.hourlyWage = hourlyWage;
	}
}
