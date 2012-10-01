package controllers;

import views.html.register;
import views.html.error;
import models.User;
import play.mvc.*;

import play.data.*;
import play.data.validation.Constraints.*;

public class Registration extends Controller {

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

	public static synchronized Result register() {
		Form<RegisterData> filledForm = registerForm.bindFromRequest();
		if(filledForm.hasErrors()) {
			return ok(register.render(filledForm));
		}

		RegisterData data = filledForm.get();

		// Check if username is taken
		User samename;
		try {
			samename = User.getByUsername(data.username);
			if(samename != null)
				filledForm.reject("username", "Username already taken.");
		} catch (Exception e) {
			e.printStackTrace(System.err);
			// Presumably a database error, but as the
			// database constraints will not allow any
			// duplicate usernames anyway, we will just
			// see what happens when we attempt to create
			// the user.
		}

		// Check if the two passwords match
		if(!data.password.equals(data.password_repeat))
			filledForm.reject("password_repeat", "Password does not match.");

		if(filledForm.hasErrors()) {
			return ok(register.render(filledForm));
		}

		try {
			new User(data.username, data.password, data.email, data.fullname);
			return ok("Registration succeded!");
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return ok(error.render(e.toString()));
		}
	}


}
