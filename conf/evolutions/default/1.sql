# --- !Ups

CREATE TABLE user (
	username TEXT NOT NULL,
	passhash TEXT NOT NULL,
	email TEXT NOT NULL,
	fullname TEXT NOT NULL,
	PRIMARY KEY(username),
	UNIQUE(email)
);

CREATE TABLE interest (
	user TEXT REFERENCES user(username),
	topic TEXT NOT NULL,
	PRIMARY KEY(user, topic)
);

# --- !Downs

DROP TABLE user;
DROP TABLE interest;
