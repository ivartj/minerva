package models;

public class Interest {
	
	public User user;
	public Topic topic;
	public String description;
	public boolean asMentor;
	public boolean asStudent;

	public Interest(User user, Topic topic, String description, boolean asMentor, boolean asStudent) {
		this.user = user;
		this.topic = topic;
		this.description = description;
		this.asMentor = asMentor;
		this.asStudent = asStudent;
	}
}
