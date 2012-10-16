package controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Query {
	
	public static String getText(String url) throws Exception
	{
		URL website = new URL(url);
		URLConnection connection= website.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		
		StringBuilder response = new StringBuilder();
		String inputLine;
		
		while((inputLine = in.readLine()) != null)
			response.append(inputLine);
		
		in.close();
		
		return response.toString();
	}
	
	public static String mox(String url)
	{
		String jox = null;
		try {
			jox = Query.getText(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String newStr = jox.replace("access_token=", "");
		System.out.println(newStr + " length " + newStr.length());
		newStr = newStr.substring(0, 111);
		
		
		return newStr;
	}
	
	public static void main(String[] args)
	{
		String content = null;
		try {
			content = Query.mox("https://graph.facebook.com/oauth/access_token?client_id=294825677288199&redirect_uri=http://localhost:8080/to&client_secret=26f2abb878e1d3991403cdadf6f275aa&code=AQCA7GPgCCrb-WntgWTnhnhEk4lT8axXtpyTRbs7Y00CvG4x4bQ2qktoTBt9dycFoIYJUXz_rm45NY9trIkiqMibVc3lXM7DRpuyiNyX8fyUu3tkfsQDCETuh0OkeDP93UfUebswwI7S7M2OHgqJinsl4wDRrIHnd2aFmDp0GY9j_l4v8RD-2cZJmTwcNCiwu-6UB8CxFWnx3-ZzxQEeG_1f#_=_");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(content);
		
		
	}

}
