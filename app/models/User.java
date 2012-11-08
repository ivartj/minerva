package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import controllers.Authenticator;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class User extends Model {

	@Id
	public Long id;
	
	public String cookieIdentifier;

	//@Required
	public String fullName;
	
	@MaxLength(50)
	public String firstName; 

	@MaxLength(50)
	public String lastName; 

	@Email
	@Required
	public String email;
	
	@Email
	public String alternativeEmail; 

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
	
	public synchronized static void create(User user) {
		user.save();
	}
	
	public static List<User> all() {
		return find.all();
	}

	public static User getByUserId(Long uid) {
		List<User> list = find.where().eq("id", uid).findList();
		if(list.size() == 0)
			return null;
		else
			return list.get(0);
	}
	
	public static boolean isUser(String id){
		return find.where().eq("googleId", id).findList().size() == 1 || find.where().eq("yahooId", id).findList().size() == 1;	
	}

	public User() {}

	public User(String fullName, String cookieIdentifier, String firstName, String lastName, Integer age, String email, String alternativeEmail, String phone, String address,  
			String nearestCity, String country) {
		this.fullName = fullName; 
		this.cookieIdentifier = cookieIdentifier; 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.age = age;
		this.email = email;
		this.alternativeEmail = alternativeEmail;
		this.phone = phone; 
		this.address = address;
		this.city = nearestCity; 
		this.country = country;
	}
	
	public void getInfo() {	
		User currentUser = Authenticator.getCurrentUser(); 
		id = currentUser.id; 
		email = currentUser.email; 
		firstName = currentUser.firstName; 
		lastName = currentUser.lastName; 
		age = currentUser.age; 
		alternativeEmail = currentUser.alternativeEmail; 
		phone = currentUser.phone; 
		address = currentUser.address; 
		city = currentUser.city; 
		country = currentUser.country;
	}
	
}
