package controllers;
import models.Emails;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import views.html.email;
import views.html.sendemail;

import com.typesafe.plugin.*;

public class Email extends Controller{

	private static final Form<Emails> emailForm = form(Emails.class);

	public static Result create(Long id) {
		User created = new User(); 
		created.id = id;
		return ok(email.render(emailForm, created));
	}

	public static Result send(Long userID) {
		Form<Emails> email = emailForm.bindFromRequest();  
		
		User currentUser = Authenticator.getCurrentUser(); 
		User toUser = User.getByUserId(userID); 
		
		Emails currentEmail = email.get();
	
		String fullName = currentUser.fullName; 
		String from = currentUser.email;
		String recipient = toUser.email;
		String subject = currentEmail.subject; 
		String text = currentEmail.text; 

		MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
		
		mail.setSubject(subject);
		mail.addRecipient(recipient);
		mail.addFrom(from);
		mail.send(fullName + " " +Language.get("Wrote") + ":\n\n"+text);
		return ok(sendemail.render(currentEmail));
	}
}