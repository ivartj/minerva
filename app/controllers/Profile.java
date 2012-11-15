package controllers;

import java.util.Map;

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
		return ok(form.render(editForm.fill(currentUser)));
	}

	/**
	 * Handle the form submission.
	 */
	public static Result submit() {
		if(checkFormToken() == false)
			return ok(error.render(Language.get("InvalidFormToken")));

		Form<User> filledForm = editForm.bindFromRequest();
		if(filledForm.hasErrors()) {
			return badRequest(form.render(filledForm));
		} else {
			User currUs = Authenticator.getCurrentUser(); 
			User currentUser = filledForm.get();  

			String s = "UPDATE user SET first_name = :first_name, last_name = :last_name, age = :age, email = :email, " +
				"phone = :phone, address = :address, city = :city, country = :country where id = :id";
			SqlUpdate update = Ebean.createSqlUpdate(s);
			update.setParameter("id", currUs.id);
			update.setParameter("first_name", currentUser.firstName);
			update.setParameter("last_name", currentUser.lastName);			
			update.setParameter("age", currentUser.age);
			update.setParameter("email", currentUser.email);
			update.setParameter("alternativeEmail", currentUser.alternativeEmail); 
			update.setParameter("phone", currentUser.phone); 
			update.setParameter("address", currentUser.address); 
			update.setParameter("city", currentUser.city); 
			update.setParameter("country", currentUser.country); 
			Ebean.execute(update);

			return ok(summary.render(currentUser));
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
		return ok(summary.render(user));      
	}

	public static Result getUser(Long userID) {
		User user = User.getByUserId(userID);
		if(user == null) {
			return ok(noUser.render(userID));
		} else {
			return ok(profile.render(user));
		}
	}
}
