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
		User currentUser= new User();
		currentUser.getInfo();
		
		String firstName = currentUser.firstName;
		String lastName = currentUser.lastName;
		Integer age = currentUser.age; 
		String email = currentUser.email; 
		String alternativeEmail = currentUser.alternativeEmail; 
		String phone = currentUser.phone; 
		String address = currentUser.address; 
		String city = currentUser.city; 
		String country = currentUser.country;
		currentUser = new User("",firstName,lastName,age,email,alternativeEmail,phone,address,city,country);
		return ok(form.render(editForm.fill(currentUser)));
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