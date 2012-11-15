package models;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

import javax.persistence.*;

import org.hibernate.validator.constraints.URL;

import controllers.Authenticator;

import play.data.validation.Constraints.*;
import play.db.ebean.*;
import play.db.DB;

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
	
	public String imageURL;
	
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
	
	/* Last minute ugly */
	 public static List<UserTableInfo> page(int page, int pageSize, String sortBy, String order, String filter) {
		 Connection conn;
		 PreparedStatement stmt;
		 String stmtStr;
		 ResultSet res;
		 List<UserTableInfo> list;

		 list = new ArrayList<UserTableInfo>();

		 stmtStr = "select id, first_name, last_name, city from user where concat(first_name, ' ', last_name) like concat('%', ?, '%') limit ? offset ?";
		 if(sortBy.equals("first_name") || sortBy.equals("last_name") || sortBy.equals("city")) {
			 stmtStr += "order by " + sortBy;
			 if(order.equals("asc") || order.equals("desc"))
				 stmtStr += " " + order;
		 }

		 try {
			 conn = DB.getConnection();
			 System.out.println(stmtStr);
			 stmt = conn.prepareStatement(stmtStr);
			 stmt.setString(1, filter);
			 stmt.setInt(2, pageSize);
			 stmt.setInt(3, pageSize * page);
			 res = stmt.executeQuery();
			 while(res.next()) {
				 UserTableInfo info = new UserTableInfo();;
				 info.id = res.getLong(1);
				 info.firstName = res.getString(2);
				 info.lastName = res.getString(3);
				 info.city = res.getString(4);
				 list.add(info);
			 }
		 } catch (SQLException e) {
			 e.printStackTrace();
		 }

		 return list;
	    }

	public static class UserTableInfo {
		public Long id;
		public String firstName;
		public String lastName;
		public String city;
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
}
