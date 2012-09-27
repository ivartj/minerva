package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Register extends Controller {

	public static Result submit() {
		return ok(index.render("Your new application is ready."));
	}

}
