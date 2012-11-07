package models;

import javax.persistence.*;

import play.db.ebean.*;

@Entity
public class Interest extends Model {
	
	@Id
	public Long userId;
	public String topic;

	public String description;
	public boolean asMentor;
	public boolean asStudent;

	public Interest(Long userId, String topic, String description, boolean asMentor, boolean asStudent) {
		this.userId = userId;
		this.topic = topic;
		this.description = description;
		this.asMentor = asMentor;
		this.asStudent = asStudent;
	}

	public User getUser() {
		return User.getByUserId(userId);
	}

	public Topic getTopic() {
		return new Topic(topic);
	}

	// I'm not sure what's supposed to be done when there are two fields as a primary key.
	public static Finder<Long,Interest> find = new Finder<Long,Interest>(Long.class, Interest.class);
}
