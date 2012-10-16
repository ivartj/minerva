package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.validation.*;

import play.data.validation.Constraints.*;
import play.db.DB;

public class User {

	public String firstName; 

	public String lastName; 

	@Required
	@Email
	public String email;

	@Valid
	@Pattern(value = "[0-9.+]+", message = "Skriv et gyldig telefonnummer")
	public String phone;

	@Valid
	public Profile profile;

	public User() {}

	public User(String firstName, String lastName, String email, String phone, Profile profile) throws SQLException {
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.email = email;
		this.phone = phone; 
		this.profile = profile;
	}

	public static class Profile {

		public String county; 
		
		
		public String country;

		public String address;
		
		@Required
		public String nearestCity; 

		@Min(18) @Max(100)
		public Integer age;

		public Profile() {}

		public Profile(String address, String nearestCity, String county, String country, Integer age) {
			this.nearestCity = nearestCity; 
			this.county = county;
			this.country = country;
			this.address = address;
			this.age = age;
		}

	}

}