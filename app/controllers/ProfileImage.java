package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Map;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import play.*;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import models.*;
import models.FormToken;

import views.html.*;

public class ProfileImage extends Controller {

	private final static int IMAGE_SIZE = 200;
	private static ArrayList<String> allowedFormats = createAllowedFormatsList();	
	
	private static ArrayList<String> createAllowedFormatsList() {
		ArrayList<String> out = new ArrayList<String>();
		out.add("image/png");
		out.add("image/jpeg");
		out.add("image/gif");
		return out;
	}

	public static String getGravatarURL(User user) {
		String gravatarURL = "http://www.gravatar.com/avatar/" + MD5(user.email.toLowerCase()) + "?s="+IMAGE_SIZE+"&d=mm";
		return gravatarURL;
	}
	
	public static Result setGravatar() {
		String givenToken;

		givenToken = getMapString(request().queryString(), "formtoken");
		if(FormToken.check("profileimage", givenToken) == false)
			return ok(error.render(Language.get("InvalidFormToken")));

		updateProfileImageURL(getGravatarURL(Authenticator.getCurrentUser()));
		return redirect(routes.Profile.edit());
	}
	
	public static Result upload() {

		String givenToken;

		MultipartFormData body = request().body().asMultipartFormData();
		FilePart imageFilePart = body.getFile("profileImage");
		String extension = imageFilePart.getContentType().substring(6);

		givenToken = getMapString(body.asFormUrlEncoded(), "formtoken");
		if(FormToken.check("profileimage", givenToken) == false)
			return ok(error.render(Language.get("InvalidFormToken")));

		
		if (imageFilePart.getFile().exists()) {
			
			if (!allowedFormats.contains(imageFilePart.getContentType())){
				flash("error", "File format not allowed");
			}
			else {
				File resizedImage = resizeImage(imageFilePart.getFile(), extension);
				writeImageFile(resizedImage, extension);
			}				
		}
		else {
			flash("error", "File missing");
		}
		return redirect(routes.Profile.edit());
	}

	private static void writeImageFile(File file, String extension){
		
		String publicPath = Play.application().path() + "/public/";
		String randomString = UUID.randomUUID().toString();
		
		String fileName = "images/profileImages/" + randomString + "." + extension;
		
		byte[] buffer = new byte[8 * 1024];		
		int bytesRead;
		
		try {
			FileInputStream is = new FileInputStream(file);			
			FileOutputStream os = new FileOutputStream(publicPath + fileName);
			
			while ((bytesRead = is.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			
			is.close();
			os.close();
			updateProfileImageURL("/assets/" + fileName);
			
		} catch (FileNotFoundException e) {
			flash("error", "File missing");
		} catch (IOException e) {
			flash("error", "File upload failed");
		}
	}
	
	private static void updateProfileImageURL(String url) {
		User user = Authenticator.getCurrentUser();
		user.imageURL = url;
		user.save();
	}
	
	public static String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {}
		return null;
	}

	private static File resizeImage(File file, String extension){
		BufferedImage in = null;
		BufferedImage out = null;
		try {
			in = ImageIO.read(file);
			out = Scalr.resize(in, IMAGE_SIZE);
			ImageIO.write(out, extension, file);
		} catch (IOException e) {
			flash("error", "Something went wrong when resizing the profile image.");
		}		
		return file;	
	}

	private static String getMapString(Map<String,String[]> map, String key) {
		String array[];

		array = map.get(key);
		if(array == null)
			return "";
		if(array.length == 0)
			return "";
		return array[0];
	}
}
