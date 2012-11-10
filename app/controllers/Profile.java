package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;

import play.mvc.*;
import play.data.*;

import views.html.*;
import views.html.signup.*;

import models.*;

public class Profile extends Controller {

	/**
	 * Defines a form wrapping the User class.
	 */ 
	final static Form<User> editForm = form(User.class);

	/**
	 * Display a blank form.
	 */ 
	public static Result edit() {
		User currentUser = Authenticator.getCurrentUser();  
		currentUser.getInfo();
		return ok(form.render(editForm.fill(currentUser)));
	}

	/**
	 * Handle the form submission.
	 */
	public static Result submit() {
        Form<User> filledForm = editForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(form.render(filledForm));
        } else {
        	User currUs = Authenticator.getCurrentUser(); 
        	User currentUser = filledForm.get(); 
        	Long id = currUs.id; 
        	String first_name = currentUser.firstName;
        	String last_name = currentUser.lastName; 
        	Integer age = currentUser.age; 
        	String email = currentUser.email; 
        	String alternativeEmail = currentUser.alternativeEmail; 
        	String phone = currentUser.phone; 
        	String address = currentUser.address; 
        	String city = currentUser.city;
        	String country = currentUser.country; 

        	String s = "UPDATE user SET first_name = :first_name, last_name = :last_name, age = :age, email = :email, " +
        			"phone = :phone, address = :address, city = :city, country = :country where id = :id";
			SqlUpdate update = Ebean.createSqlUpdate(s);
			update.setParameter("id", id);
			update.setParameter("first_name", first_name);
			update.setParameter("last_name", last_name);			
			update.setParameter("age", age);
			update.setParameter("email", email);
			update.setParameter("alternativeEmail", alternativeEmail); 
			update.setParameter("phone", phone); 
			update.setParameter("address", address); 
			update.setParameter("city", city); 
			update.setParameter("country", country); 
			
			Ebean.execute(update);
			
			return ok(summary.render(currentUser));
        }
    }
	
	public static Result profile(){
        User user = Authenticator.getCurrentUser();
        return ok(summary.render(user));      
    }
    
    public static Result getUser(Long userID){
        User user = User.getByUserId(userID);
        if(user == null){
            return ok(noUser.render(userID));
        }else{
            return ok(profile.render(user));
        }
    }
}