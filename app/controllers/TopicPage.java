package controllers;

import views.html.topicpage;
import views.html.error;
import play.mvc.*;
import models.User;
import models.Topic;
import controllers.Language;
import java.util.Map;

public class TopicPage extends Controller {


	public static Result page(String topicName) {
		User user = Authenticator.getCurrentUser();
		return ok(topicpage.render(topicName, user));
	}

	// TODO: Protect against cross-site request forgeries
	public static Result apply(String topicName) {
		User user = null;
		Topic topic;
		String description;
		boolean asMentor, asStudent, remove;
		String tmp[];
		Map<String, String[]> formData;

		topic = new Topic(topicName);

		formData = request().body().asFormUrlEncoded();

		remove = getFormString(formData, "remove") != "";
		description = getFormString(formData, "description");
		asMentor = getFormString(formData, "as_mentor") != "";
		asStudent = getFormString(formData, "as_student") != "";

		user = Authenticator.getCurrentUser();

		if(remove) {
			try {
				topic.removeUser(user);
				return ok(topicpage.render(topicName, user));
			} catch (Exception e) {
				e.printStackTrace();
				return ok(error.render(e.toString()));
			}
		}

		if(user == null)
			return ok(error.render("You can't apply to a topic without being logged in!"));

		try {
			topic.addUser(user, description, asMentor, asStudent);
		} catch (Exception e) {
			e.printStackTrace();
			return ok(error.render(e.toString()));
		}

		return ok(topicpage.render(topicName, user));
	}

	private static String getFormString(Map<String, String[]> formData, String key) {
		String array[];

		array = formData.get(key);
		if(array == null)
			return "";
		if(array.length == 0)
			return "";
		return array[0];
	}

}
