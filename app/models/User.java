package models;

import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.*;
import play.db.ebean.*;

import com.avaje.ebean.*;

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
	
	 public static Page<User> page(int page, int pageSize, String sortBy, String order, String filter) {
	        return 
	            find.where()
	                .ilike("fullName", "%" + filter + "%")
	                .orderBy(sortBy + " " + order)
	                .findPagingList(pageSize)
	                .getPage(page);
	    }

	public User() {}

	public User(String fullName, String firstName, String lastName, String email, String alternativeEmail, String phone, String address, 
			String nearestCity, String country, Integer age) {
		this.fullName = fullName; 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.email = email;
		this.alternativeEmail = alternativeEmail; 
		this.phone = phone; 
		this.city = nearestCity; 
		this.country = country;
		this.address = address;
		this.age = age;
	}
}
