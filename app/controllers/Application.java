package controllers;

import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Lists;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.User;

import play.*;
import play.api.mvc.Session;
import play.mvc.*;
import play.data.*;
import play.libs.*;
import views.html.*;
import models.*;
import play.api.libs.oauth.*;


public class Application extends Controller {


	private final static String MY_ACCESS_TOKEN = "AAACEdEose0cBABHKBGZASqebGrqRrfPvW36uIwffxCHWcJwHmmWHS0TpRSiFqL5EZCzS1QMZCfjp8aLHKDK5jZCp3AHmZC04KJaHR32jKlVqhOmVpDpQp";
	private final static String FACEBOOK_API_URL = "https://www.facebook.com/dialog/oauth?client_id=271131522987567&redirect_uri=http://localhost:9000/&scope=user_about_me";
	private final static String MY_APP_ID = "294825677288199";
	private final static String MY_APP_SECRET = "26f2abb878e1d3991403cdadf6f275aa";
	private final static String TEMP = "AQC-Hlv_Yt32cutSeDgtmT0pWLcH-R-kbvmHf1PP4A_-HkKCa_dTHgGuinCTy3Liux75xRhCfnIzz2-vctMspE-oxDMnsim7-oqV8JOl48pyLCm90us2lq_eM219xd--I9Y6SoI20DlvAoUqIdjFI913j69B4DFR70Tqj0HWflnn6bvJflWtgBPoC6Y5Rt7IW_YiR2wOebLCp3SPcEnVV33o";
	private final static String MY_REDIRECT_URL = "http://localhost:8080/to";

	private static boolean token = false;
	




	public static Result welcome()
	{
		return ok("Goto /go");
	}

	public static Result redir()
	{
		String url = "https://www.facebook.com/dialog/oauth?client_id="+ MY_APP_ID + "&redirect_uri=" + MY_REDIRECT_URL+ "&scope_about_me";
		return redirect(url);
	}

	public static Result index(String code)
	{
		//Second login, now with string
		String ost = Query.mox("https://graph.facebook.com/oauth/access_token?client_id=" + MY_APP_ID+ "&redirect_uri=" + MY_REDIRECT_URL + "&client_secret=" + MY_APP_SECRET + "&code=" + code);

		
		
		System.out.println("Facebook auth code: " + ost);
		User userDetails = ctf(ost);
		return ok("About you: " + userDetails.getAbout() + " Your email: " + userDetails.getEmail() +"\n"
				+ "Your name is: " + userDetails.getName());
	}

	public static User ctf(String ost)
	{

		System.out.println(ost);
		FacebookClient facebookClient = new DefaultFacebookClient(ost);
		
		User user = facebookClient.fetchObject("me", User.class);		
		return user;
	}

	public static Result bullshit(String code)
	{
		return ok("Code is: " + code);
	}
}


