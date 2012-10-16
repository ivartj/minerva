package controllers;


import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

public class FBtest {
	
	
	
	private static final String FACEBOOK_API_KEY = "xxxxx";
	private static final String FACEBOOK_APPLICATION_SECRET = "xxxxx";
	private final static String MY_ACCESS_TOKEN = "AAACEdEose0cBAHSuPyEcNrrBFdZAvBiFhXMrH5wBLSLeGxrcCNBysBitSZB6IrOEl0qT4sKPZAYmuMtXygaAKUuBjqLTQoIz6DeKEHt3IHGe6SZB4Qac";
	private static FacebookClient facebookClient;
	private static FacebookClient publicFacebookClient;
	private String facebookAuthToken = null;
	private String facebookSessionKey = null;
	private static User user;
	
	public  FBtest()
	{
	
	
	}
	
	public static void woah2()
	{
		facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN);
		publicFacebookClient = new DefaultFacebookClient();
		user = facebookClient.fetchObject("me", User.class);
	}
	
	public static User getUser()
	{
		return user;
	}

}
