# --- !Ups

CREATE TABLE user (
	id INTEGER NOT NULL,
	username TEXT NOT NULL,
	password TEXT NOT NULL,
	email TEXT NOT NULL,
	fullname TEXT NOT NULL,
	PRIMARY KEY(id)
);

# --- !Downs

DROP TABLE user;
