package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import play.*;
import play.mvc.*;
import play.data.*;
import play.db.DB;

import views.html.signup.*;

import models.*;

public class EditProfile extends Controller {

	/**
	 * Defines a form wrapping the User class.
	 */ 
	final static Form<User> editForm = form(User.class);

	/**
	 * Display a blank form.
	 * @throws SQLException 
	 */ 
	public static Result edit() {
		User currentUser= new User();
		currentUser.getInfo();
		return ok(form.render(editForm.fill(currentUser)));
	}

	/**
	 * Handle the form submission.
	 */
	public synchronized static Result submit() {
		Form<User> filledForm = editForm.bindFromRequest();
		User created;
		if(filledForm.hasErrors()) {
			return badRequest(form.render(filledForm));
		} else {
			created = Authenticator.getCurrentUser();
			created = filledForm.get();
			
			User.create(created);
			return ok(summary.render(created));
		}
	}
}