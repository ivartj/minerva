package controllers;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	static Form<User> userForm = form(User.class);
	
	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}
	
	public static Result newUser() {
		Form<User> filledForm = userForm.bindFromRequest();
//		if (filledForm.hasErrors()) {
//			return badRequest(views.html.index.render(User.all(), filledForm));
//		}
//		else {
			User.create(filledForm.get());
			return redirect(routes.Application.index());
//		}
	}
}