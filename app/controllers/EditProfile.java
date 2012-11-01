package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import views.html.signup.*;

import models.*;

public class EditProfile extends Controller {

	/**
	 * Defines a form wrapping the User class.
	 */ 
	final static Form<User> editForm = form(User.class);

	/**
	 * Display a blank form.
	 */ 
	public static Result edit() {
		User existingUser = new User();
		existingUser.getInfo();
		
		String firstName = existingUser.firstName;
		String lastName = existingUser.lastName;
		Integer age = existingUser.age; 
		String email = existingUser.email; 
		String alternativeEmail = existingUser.alternativeEmail; 
		String phone = existingUser.phone; 
		String address = existingUser.address; 
		String city = existingUser.city; 
		String country = existingUser.country; 
		
		existingUser = new User("",firstName,lastName,age,email,alternativeEmail,phone,address,city,country);
		return ok(form.render(editForm.fill(existingUser)));
	}

	/**
	 * Handle the form submission.
	 */
	public synchronized static Result submit() {
		Form<User> filledForm = editForm.bindFromRequest();

		if(filledForm.hasErrors()) {
			return badRequest(form.render(filledForm));
		} else {
			User created = filledForm.get();
			User.create(created);
			return ok(summary.render(created));
		}
	}

}