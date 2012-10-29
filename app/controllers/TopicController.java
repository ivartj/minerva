package controllers;

import views.html.topicpage;
import views.html.error;
import play.mvc.*;
import models.User;
import models.Topic;
import controllers.Language;

public class TopicController extends Controller {


	public static Result page(String topicName) {
		return ok(topicpage.render(topicName));
	}

	// TODO: Protect against cross-site request forgeries
	public static Result apply(String topicName) {
		User user = null;
		Topic topic;

		topic = new Topic(topicName);

		// TODO
		// user = User.getCurrentUser();
		if(User.all().size() != 0)
			user = User.all().get(0);

		if(user != null) {
			try {
				topic.addUser(user);
			} catch (Exception e) {
				try {
					if(topic.hasUser(user))
						return ok(error.render(Language.get("AlreadyApplied")));
				} catch(Exception ee) {
					return ok(ee.toString());
				}
				e.printStackTrace();
				return ok(error.render(e.toString()));
			}
		}

		return ok(topicpage.render(topicName));
	}

}
