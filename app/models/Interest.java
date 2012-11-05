package models;

public class Interest {
	
	public Long userId;
	public Topic topic;
	public String description;
	public boolean asMentor;
	public boolean asStudent;

	public Interest(Long userId, Topic topic, String description, boolean asMentor, boolean asStudent) {
		this.userId = userId;
		this.topic = topic;
		this.description = description;
		this.asMentor = asMentor;
		this.asStudent = asStudent;
	}

	public User getUser() {
		return User.getByUserId(userId);
	}
}
