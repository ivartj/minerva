package controllers;

import views.html.myTopics;
import views.html.topicpage;
import views.html.gettopic;
import views.html.error;
import views.html.topics; 
import play.mvc.*;
import models.User;
import models.Topic;
import models.FormToken;
import controllers.Language;
import java.util.Map;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

public class TopicPage extends Controller {

	public static Result getTopics() {
    	User user = Authenticator.getCurrentUser(); 
		if (user == null) {
			return redirect("/");
		}
		else { 
			return ok(topics.render());
		}
    	
    }
	public static Result page(String topicName) {
		User user = Authenticator.getCurrentUser();
		return ok(topicpage.render(topicName, user));
	}

	public static Result getTopic() {
		Map<String, String[]> formData;
		String topicName, url;
		String errMsg;
		boolean submit;

		formData = request().queryString();
		topicName = getFormString(formData, "topic");
		submit = getFormString(formData, "submit") != "";
		if(topicName == "") {
			errMsg = submit ? Language.get("EmptyTopicName") : "";
			return ok(gettopic.render(errMsg));
		}
		try {
			url = "/topic/" + URLEncoder.encode(topicName, "UTF-8");
		} catch(UnsupportedEncodingException e) {
			url = "/topic/" + topicName;
		}
		return redirect(url);
	}

	public static Result apply(String topicName) {
		User user = null;
		Topic topic;
		String description;
		boolean asMentor, asStudent, remove;
		String tmp[];
		Map<String, String[]> formData;

		topic = new Topic(topicName);

		formData = request().body().asFormUrlEncoded();

		if(FormToken.check("topicpage", getFormString(formData, "form-token")) == false)
			return ok(error.render(Language.get("InvalidFormToken")));

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
