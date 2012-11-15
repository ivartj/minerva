package controllers;
import models.Emails;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import com.typesafe.plugin.*;

public class Email extends Controller{

	private static final Form<Emails> emailForm = form(Emails.class);

	public static Result create(Long id) {
		User currentUser = Authenticator.getCurrentUser(); 
		if (currentUser == null) {
			return redirect("/");
		}
		else {
			User created = new User(); 
			created.id = id;
			return ok(email.render(emailForm, created));
		}
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
		mail.addFrom("Minerva <"+from+">");
		mail.send(Language.get("ReceivedMessage")+"\n\n"+fullName + " " +Language.get("Wrote") + ":\n\n"+text+"\n\n\n"+Language.get("Reply")+": "+currentUser.email);
		return ok(emailSent.render(currentEmail));
	}
}