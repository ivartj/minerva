package controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import models.User;
import models.FormToken;

import play.data.Form;
import play.libs.F.Promise;
import play.libs.OpenID;
import play.libs.OpenID.UserInfo;
import play.mvc.Controller;
import play.mvc.Http.Cookie;
import play.mvc.Result;
import views.html.login;
import views.html.confirm;
import views.html.error;

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
		attributes.put("email", "http://axschema.org/contact/email");
		attributes.put("fullname", "http://axschema.org/namePerson");

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
				user.firstName = userInfo.attributes.get("oiFirstName");
				user.lastName = userInfo.attributes.get("oiLastName");
				user.email = userInfo.attributes.get("oiEmail");
			}
			else if (userInfo.id.startsWith(identifiers.get("yahoo"))){
				
				String fullName = userInfo.attributes.get("fullname");
				
				user.yahooId = userInfo.id;
				user.email = userInfo.attributes.get("email");
				user.firstName = fullName.split("[ ]")[0].trim();
				user.lastName = fullName.substring(fullName.split("[ ]")[0].trim().length()).trim();
			}
			else
				return unauthorized("Hmm");
			return ok(confirm.render(user, userForm));	    
		}
		else{
			
			if (User.find.where().eq("googleId", userInfo.id).findList().size() == 1)
				remember(User.find.where().eq("googleId", userInfo.id).findList().get(0));
			else if (User.find.where().eq("yahooId", userInfo.id).findList().size() == 1)
				remember(User.find.where().eq("yahooId", userInfo.id).findList().get(0));
			
			return redirect(routes.Application.index());
		}
	}

	public static Result confirmUser() {
		Form<User> filledForm = userForm.bindFromRequest();
		User user = filledForm.get();
		user.fullName = user.firstName + " " + user.lastName;
		user.imageURL = ProfileImage.getGravatarURL(user);
		User.create(user);
		remember(user);
		return redirect(routes.Application.index());
	}

	public static Result logout() {
		if(FormToken.check("logout", getMapString(request().queryString(), "formtoken")) == false)
			return ok(error.render(Language.get("InvalidFormToken")));
		User user = getCurrentUser();
		user.cookieIdentifier = "";
		user.save();
		response().discardCookies("rememberMe");
		return redirect(routes.Application.index());
	}

	private static void remember(User user) {
		String uuid = user.id + UUID.randomUUID().toString();
		user.cookieIdentifier = uuid;
		user.save();
		response().setCookie("rememberMe", uuid);
	}

	public static User getCurrentUser(){
		Cookie cookie = request().cookies().get("rememberMe");
		if (cookie == null)
			return null;
		User user = User.find.where().eq("cookieIdentifier", cookie.value()).findUnique();
		return user;
	}

	private static String getMapString(Map<String, String[]> map, String key) {
		String array[];

		array = map.get(key);
		if(array == null)
			return "";
		if(array.length == 0)
			return "";
		return array[0];
	}
}
