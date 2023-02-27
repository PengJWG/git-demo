package com.pli.jpa.hibernate.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
	
	private String city;
	private String street;
	private String number;
	
	protected Address() {
		
	}
	
	public Address(String city, String street, String number) {
		super();
		this.city = city;
		this.street = street;
		this.number = number;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

}
