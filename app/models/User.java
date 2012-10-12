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
	public String FullName;
	
	@Required
	@Email
	public String Email;
	
	public String City;

	public String GoogleId;
	
	public static Finder<Long,User> find = new Finder<Long,User>(Long.class, User.class);
	
	public static void create(User user) {
		user.save();
	}
	
	public static List<User> all() {
		return find.all();
	}
	
	public static boolean isGoogleUser(String id){
		return find.where().eq("GoogleId", id).findList().size() == 1;			
	}
	
}
