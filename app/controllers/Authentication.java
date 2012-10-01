package controllers;

import views.html.user.*;
import models.User;
import play.mvc.*;

import play.data.*;
import play.data.validation.Constraints.*;

public class Authentication extends Controller {

	final static Form<RegisterData> registerForm = form(RegisterData.class);

	public static Result registerForm() {
		return ok(register.render(registerForm));
	}

	public static class RegisterData {
		@Required
		@MinLength(1)
		public String username;

		@Required
		@MinLength(7)
		public String password;
		public String password_repeat;

		@Required
		@Email
		public String email;

		@Required
		@MinLength(1)
		public String fullname;
	}

	public static Result register() {
		Form<RegisterData> filledForm = registerForm.bindFromRequest();
		if(filledForm.hasErrors()) {
			return ok(register.render(filledForm));
		}

		RegisterData data = filledForm.get();

		// Check if username is already taken
		if(filledForm.error("username") == null) {
			User samename;
			try {
				samename = User.getByUsername(data.username);
			} catch (Exception e) {
				return ok("Unable to check whether your username is taken.");
			}
			if(samename != null)
				filledForm.reject("username", "Username already taken.");
		}

		// Check if the two passwords match
		if(filledForm.error("password") == null && data.password != data.password_repeat)
			filledForm.reject("password_repeat", "Password does not match.");

		if(filledForm.hasErrors()) {
			return ok(register.render(filledForm));
		}

		try {
			new User(data.username, data.password, data.email, data.fullname);
			return ok("Registration succeded!");
		} catch (Exception e) {
			return ok("Database error");
		}
	}

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
			return ok("Database error");
		}
	}


}
