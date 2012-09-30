package controllers;

import views.html.user.*;
import models.User;
import play.mvc.*;

public class Authentication extends Controller {

	public static Result registerForm() {
		return ok(register.render());
	}

	public static class RegisterData {
		public String username;
		public String password;
		public String password_repeat;
		public String email;
		public String fullname;
	}

	public static Result register() {
		RegisterData form = form(RegisterData.class).bindFromRequest().get();

		try {
			new User(form.username, form.password, form.email, form.fullname);
			return ok("Registration succeded!");
		} catch (Exception e) {
			return ok("Database error");
		}
	}

	public static Result loginForm() {
		return ok(login.render());
	}

	public static class LoginData {
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
			return ok("Database error");
		}
	}


}
