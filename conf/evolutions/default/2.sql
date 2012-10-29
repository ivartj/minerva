# --- !Ups

-- relation table between users and topics
create table interest (
	topic text not null,
	user integer not null,
	foreign key(user) references user(id),
	primary key(user, topic)
);

# --- !Downs

drop table interest;
