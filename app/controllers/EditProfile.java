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
	public static Result blank() {
		return ok(form.render(editForm));
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