package controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import models.User;

import play.data.Form;
import play.libs.F.Promise;
import play.libs.OpenID;
import play.libs.OpenID.UserInfo;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import views.html.confirm;

public class Authenticator extends Controller{
	
	static Form<User> userForm = form(User.class);
	
	public static final Map<String, String> identifiers = createMap();	
	
	public static Result chooseProvider() {
		return ok(login.render());
	}
		
	private static Map<String, String> createMap() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("google", "https://www.google.com/accounts/o8/id");
		result.put("yahoo", "https://me.yahoo.com");
		return Collections.unmodifiableMap(result);
	}

	public static Result auth() {
	
		String providerId = request().body().asFormUrlEncoded().get("provider")[0];
		String providerUrl = identifiers.get(providerId);
	    String returnToUrl = "http://" + request().getHeader("Host") + "/verify";

	    Map<String, String> attributes = new HashMap<String, String>();
	    attributes.put("oiEmail", "http://schema.openid.net/contact/email");
	    attributes.put("oiFirstName", "http://schema.openid.net/namePerson/first");
	    attributes.put("oiLastName", "http://schema.openid.net/namePerson/last");
	    attributes.put("oiCity", "http://openid.net/schema/contact/city/home");
	    attributes.put("axEmail", "http://axschema.org/contact/email");
	    attributes.put("axFullName", "http://axschema.org/namePerson");

	    Promise<String> redirectUrl = null;
		redirectUrl = OpenID.redirectURL(providerUrl, returnToUrl, attributes);
		
	    return redirect(redirectUrl.get());
	}

	public static Result verify() {
	    Promise<UserInfo> userInfoPromise = OpenID.verifiedId();
	    UserInfo userInfo = userInfoPromise.get();
	    
	    if (!User.isUser(userInfo.id)){
		    
	    	User user = new User();
		    if (userInfo.id.startsWith(identifiers.get("google"))){

		    	user.googleId = userInfo.id;
		    	user.fullName = userInfo.attributes.get("oiFirstName") + " " + userInfo.attributes.get("oiLastName");
		    	user.email =  userInfo.attributes.get("oiEmail");
		    }
		    else if (userInfo.id.startsWith(identifiers.get("yahoo"))){
		 
		    	user.fullName = userInfo.attributes.get("fullname");
	    		user.email = userInfo.attributes.get("email");
		    	user.yahooId = userInfo.id;
		    }
		    else
		    	return unauthorized("Hmm");
		    
		    return ok(confirm.render(user, userForm));	    
	    }
	    else
	    	return redirect(routes.Application.index());    
	}
	
	public static Result confirmUser() {
		Form<User> filledForm = userForm.bindFromRequest();
		User.create(filledForm.get());
		return redirect(routes.Application.index());

	}
}
