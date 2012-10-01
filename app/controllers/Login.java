package controllers;

import views.html.login;
import views.html.error;
import models.User;
import play.mvc.*;

import play.data.*;
import play.data.validation.Constraints.*;

public class Login extends Controller {

	public static Result loginForm() {
		return ok(login.render());
	}

	public static class LoginData {
		@Required
		public String username;
		public String password;
	}

	public static Result login() {
		LoginData form = form(LoginData.class).bindFromRequest().get();

		try {
			if(User.authenticate(form.username, form.password))
				return ok("Login succeded!");
			else
				return ok("Login failed");
		} catch (Exception e) {
			return ok(error.render(e.toString()));
		}
	}

}
