package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class User extends Model {

	@Id
	public Long id;

	//@Required
	public String fullName;
	
	@MaxLength(50)
	public String firstName; 

	@MaxLength(50)
	public String lastName; 

	@Email
	@Required
	public String email;

	@Pattern(value = "[0-9.+]+", message = "Skriv et gyldig telefonnummer")
	public String phone;

	public String country;

	@MaxLength(100)
	public String address;

//	@Required
	public String city; 

	@Min(18) @Max(150)
	public Integer age;

	public String googleId;
	
	public String yahooId;

	
	public static Finder<Long,User> find = new Finder<Long,User>(Long.class, User.class);
	
	public static void create(User user) {
		user.save();
	}
	
	public static List<User> all() {
		return find.all();
	}
	
	public static boolean isUser(String id){
		return find.where().eq("googleId", id).findList().size() > 0 || find.where().eq("yahooId", id).findList().size() > 0;	
	}

	public User() {}

	public User(String fullName, String firstName, String lastName, String email, String phone, String address, 
			String nearestCity, String country, Integer age) {
		this.fullName = fullName; 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.email = email;
		this.phone = phone; 
		this.city = nearestCity; 
		this.country = country;
		this.address = address;
		this.age = age;
	}
}