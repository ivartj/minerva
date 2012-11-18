package controllers;

import java.util.Map;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;

import play.mvc.*;
import play.data.*;

import views.html.*;

import models.*;

public class Profile extends Controller {

	final static Form<User> editForm = form(User.class);

	public static Result edit() {
		User currentUser = Authenticator.getCurrentUser();
		return ok(editProfile.render(editForm.fill(currentUser)));
	}

	public static Result submit() {
		if(checkFormToken() == false)
			return ok(error.render(Language.get("InvalidFormToken")));

		Form<User> filledForm = editForm.bindFromRequest();
		if(filledForm.hasErrors()) {
			return badRequest(editProfile.render(filledForm));
		} else {
			User currUs = Authenticator.getCurrentUser(); 
			User currentUser = filledForm.get();  

			String s = "UPDATE user SET first_name = :first_name, last_name = :last_name, age = :age, email = :email, " +
				"phone = :phone, address = :address, city = :city, country = :country where id = :id";
			SqlUpdate update = Ebean.createSqlUpdate(s);
			update.setParameter("id", currUs.id);
			update.setParameter("first_name", currentUser.firstName);
			update.setParameter("last_name", currentUser.lastName);	
			update.setParameter("full_name", currentUser.firstName +" "+currentUser.lastName);
			update.setParameter("age", currentUser.age);
			update.setParameter("email", currentUser.email);
			update.setParameter("alternativeEmail", currentUser.alternativeEmail); 
			update.setParameter("phone", currentUser.phone); 
			update.setParameter("address", currentUser.address); 
			update.setParameter("city", currentUser.city); 
			update.setParameter("country", currentUser.country); 
			Ebean.execute(update);

			return ok(userProfile.render(currentUser));
        }
    }

	private static boolean checkFormToken() {
		Map<String, String[]> formMap;
		String[] array;
		String receivedToken;

		formMap = request().body().asFormUrlEncoded();
		array = formMap.get("formtoken");
		if(array == null)
			return false;
		if(array.length == 0)
			return false;
		receivedToken = array[0];
		return FormToken.check("editprofile", receivedToken);
	}

	public static Result profile() {
    	User user = Authenticator.getCurrentUser(); 
		if (user == null) {
			return redirect("/");
		}
		else { 
			return ok(userProfile.render(user));
		}
	}

	public static Result getUser(Long userID) {
		User user = User.getByUserId(userID);
		if(user == null) {
			return ok(noUser.render(userID));
		} else {
			return ok(profile.render(user));
		}
	}
	public static Result myTopics() {
    	User user = Authenticator.getCurrentUser(); 
		if (user == null) {
			return redirect("/");
		}
		else { 
			return ok(myTopics.render(user));
		}
    	
    }
}
