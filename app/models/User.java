package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.*;
import play.db.DB;
import play.db.ebean.Model;
import play.api.db.*;

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
	
	public static boolean isUser(String id){
		return find.where().eq("googleId", id).findList().size() == 1 || find.where().eq("yahooId", id).findList().size() == 1;	
	}

	public User() {}

	public User(String fullName, String firstName, String lastName, Integer age, String email, String alternativeEmail, String phone, String address,  
			String nearestCity, String country) {
		this.fullName = fullName; 
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
		try {		
			Connection conn = DB.getConnection();
			Statement setning = conn.createStatement();
			String sporring = "select full_name, first_name, last_name, age, email, alternative_Email, phone, address, "+
								"city, country from user where id=1";
			ResultSet resultat = setning.executeQuery(sporring);
			fullName = resultat.getString(1);
			firstName = resultat.getString(2); 
			lastName = resultat.getString(3); 
			age = resultat.getInt(4); 
			email = resultat.getString(5); 
			alternativeEmail = resultat.getString(6); 
			phone = resultat.getString(7);
			address = resultat.getString(8); 
			city = resultat.getString(9); 
			country = resultat.getString(10); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
