package controllers;

import java.sql.SQLException;

import play.*;
import play.mvc.*;
import play.data.*;

import views.html.signup.*;
import views.html.*;

import models.*;

public class SignUp extends Controller {
    
    /**
     * Defines a form wrapping the User class.
     */ 
    final static Form<User> signupForm = form(User.class);
    
  
    /**
     * Display a blank form.
     */ 
    public static Result blank() {
        return ok(form.render(signupForm));
    }
   
    /**
     * Handle the form submission.
     */
    public static Result submit() {
        Form<User> filledForm = signupForm.bindFromRequest();
        
        // Check accept conditions
        if(!"true".equals(filledForm.field("accept").value())) {
            filledForm.reject("accept", "You must accept the terms and conditions");
        }

        if(filledForm.hasErrors()) {
            return badRequest(form.render(filledForm));
        } else {
            User created = filledForm.get();
            return ok(summary.render(created));
        }
    }
    
    public static Result profile(){
        User user = Authenticator.getCurrentUser();
        return ok(profile.render(user));
        
    }

}