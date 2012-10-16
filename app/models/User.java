package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.validation.*;

import play.data.validation.Constraints.*;
import play.db.DB;

public class User {

	@MaxLength(50)
	public String firstName; 

	@MaxLength(50)
	public String lastName; 

	@Required
	@Email
	public String email;

	@Valid
	@Pattern(value = "[0-9.+]+", message = "Skriv et gyldig telefonnummer")
	public String phone;
	
	public String country;

	@MaxLength(100)
	public String address;

	@Required
	public String nearestCity; 

	@Min(18) @Max(150)
	public Integer age;


	public User() {}

	public User(String firstName, String lastName, String email, String phone, String address, 
			String nearestCity, String country, Integer age) {
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.email = email;
		this.phone = phone; 
		this.nearestCity = nearestCity; 
		this.country = country;
		this.address = address;
		this.age = age;
	}


}