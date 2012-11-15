package models;

import java.util.List;

import javax.persistence.Entity;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Emails extends Model{

	public String from;
	public String recipient;
	public String subject; 
	public String text; 
	
	public static Finder<Long,Emails> find = new Finder<Long,Emails>(Long.class, Emails.class);
	
	public static List<Emails> all() {
		return find.all();
	}
	
	public Emails() {}
	
	public Emails(String fullName, String from, String recipient, String subject, String text) {
		this.from = from; 
		this.recipient = recipient;
		this.subject = subject; 
		this.text = text; 
	}	
}