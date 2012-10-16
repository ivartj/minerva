package models;

import java.util.*;

public class Countries {
	
    public static List<String> list() {
        List<String> all = new ArrayList<String>();
        
        String[] isoCountries = Locale.getISOCountries();
        
        for (String country: isoCountries) {
        	Locale locale = new Locale("en", country);
        	String name = locale.getDisplayCountry(); 
        	
        	if (!"".equals(name)) {
        		all.add(name); 
        	}
        }
        Collections.sort(all); 
        return all;
    }
}
