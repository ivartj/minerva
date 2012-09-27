package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import models.User;

public class Register extends Controller {

	public static Result submit() {
		try {
			new User("ivartj", "password", "it.jarlsby@stud.uis.no", "Ivar Trygve Jarlsby"); 	
			return ok("SUCCESS");
		} catch (Exception e) {
			return ok(e.toString());
		}
	}

}
