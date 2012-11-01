package controllers;

import java.util.*;

import play.*;
import play.mvc.*;
import play.data.*;
import models.*;
import views.html.*;

public class Subject extends Controller
{
	
	
private static ArrayList<String> emner;
	
	public static Result send()
	{
		emner = new ArrayList<String>();
		emner.add("Java");
		emner.add("C");
		emner.add("Play Framework");
		emner.add("Banana");
		emner.add("Muring");
		emner.add("Rokk");
		emner.add("Scala");
		emner.add("Delegering");
		emner.add("Bavian vasking");
		emner.add("Traktor");
		emner.add("Rectoskopi");
		
		return ok(subjects.render());
	}
	
	static String[] emnerKan = null;
	static String[] emnerVil = null;

	public static Result post()
	{
		final Map<String, String[]> values = request().body().asFormUrlEncoded();
		emnerVil = values.get("selectedWant[]");
		emnerKan = values.get("selectedCan[]");
		 String ost = "Emner har lyst på: ";
		for(int i =0; i<emnerVil.length; i++)
		{
			ost += emnerVil[i] + ", ";

		}
		ost += ". Emner kan: ";
		
		for(int i =0; i<emnerKan.length; i++)
		{
			ost += emnerKan[i] + ", ";

		}
		ost += ".";
		
		System.out.println(ost);

		return ok(subjectDisplay.render(new ArrayList<String>(Arrays.asList(emnerVil)), new ArrayList<String>(Arrays.asList(emnerKan))));
	}

}

