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

	@Required
	public String fullName;
	
	@Email
	@Required
	public String email;
	
	public String city;

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
}