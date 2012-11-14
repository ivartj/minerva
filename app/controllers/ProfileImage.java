package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import play.*;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import models.*;

import views.html.*;

public class ProfileImage extends Controller {

	final static int IMAGE_SIZE = 200;
	static User currentUser = Authenticator.getCurrentUser();
	static ArrayList<String> allowedFormats = createAllowedFormatsList();	
	
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
		updateProfileImageURL(getGravatarURL(currentUser));
		return redirect(routes.Profile.edit());
	}
	
	public static Result upload() {

		MultipartFormData body = request().body().asMultipartFormData();
		FilePart imageFilePart = body.getFile("profileImage");
		String extension = imageFilePart.getContentType().substring(6);
		
		if (imageFilePart.getFile().exists()) {
			
			if (!allowedFormats.contains(imageFilePart.getContentType())){
				flash("error", "File format not allowed");
			}
			else {
				
				File resizedImage = resizeImage(imageFilePart.getFile(), extension);
				
				if(writeImageFile(resizedImage, extension)){ 
				}
				else 
					flash("error", "File upload failed");	
			}				
		}
		else {
			flash("error", "Missing file");
		}
		return redirect(routes.Profile.edit());
	}

	private static boolean writeImageFile(File file, String extension){
		
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
			return true;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("end of writefImageFile");
		return false;
	}
	
	private static void updateProfileImageURL(String url) {
		currentUser.imageURL = url;
		currentUser.save();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return file;	
	}
}
