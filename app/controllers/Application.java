package controllers;

import models.User;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	static Form<User> userForm = form(User.class);
	
	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}
	
}