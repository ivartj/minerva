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
		return ok(topicpage.render(topicName));
	}

	// TODO: Protect against cross-site request forgeries
	public static Result apply(String topicName) {
		User user = null;
		Topic topic;
		String description;
		boolean asMentor, asStudent;
		String tmp[];
		Map<String, String[]> formData;

		topic = new Topic(topicName);

		formData = request().body().asFormUrlEncoded();
		description = getFormString(formData, "description");
		asMentor = getFormString(formData, "as_mentor") != "";
		asStudent = getFormString(formData, "as_student") != "";

		user = Authenticator.getCurrentUser();

		if(user == null)
			return ok(error.render("You can't apply to a topic without being logged in!"));

		try {
			topic.addUser(user, description, asMentor, asStudent);
		} catch (Exception e) {
			try {
				if(topic.hasUser(user))
					return ok(error.render(Language.get("AlreadyApplied")));
			} catch(Exception ee) {
				return ok(error.render(ee.toString()));
			}
			e.printStackTrace();
			return ok(error.render(e.toString()));
		}

		return ok(topicpage.render(topicName));
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
