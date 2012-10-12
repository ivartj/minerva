package controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import models.User;

import play.libs.F.Promise;
import play.libs.OpenID;
import play.libs.OpenID.UserInfo;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import views.html.confirm;
import views.html.index;

public class Authenticator extends Controller{
	
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
	    attributes.put("Email", "http://schema.openid.net/contact/email");
	    attributes.put("FirstName", "http://schema.openid.net/namePerson/first");
	    attributes.put("LastName", "http://schema.openid.net/namePerson/last");
	    attributes.put("City", "http://openid.net/schema/contact/city/home");

	    Promise<String> redirectUrl = null;
		redirectUrl = OpenID.redirectURL(providerUrl, returnToUrl, attributes);
		
	    return redirect(redirectUrl.get());
	}

	public static Result verify() {
	    Promise<UserInfo> userInfoPromise = OpenID.verifiedId();
	    UserInfo userInfo = userInfoPromise.get();
	    
	    if (!User.isGoogleUser(userInfo.id)){
		    User user = new User();
	    	user.FullName = userInfo.attributes.get("FirstName") + " " + userInfo.attributes.get("LastName");
		    user.Email =  userInfo.attributes.get("Email");
		    user.GoogleId = userInfo.id;	    
		    return ok(confirm.render(user));
	    }
	    else
	    	return redirect(routes.Application.index());
	    
	}
}
