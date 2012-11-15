# --- !Ups

-- relation table between users and topics
create table interest (
	topic varchar(255) not null,
	user integer not null,
	description text not null,
	as_mentor integer not null,
	as_student integer not null,
	foreign key(user) references user(id),
	primary key(user, topic)
);

create table topic_description (
	topic varchar(255) not null,
	description text not null,
	primary key(topic)
);

create view interest_topic_distinct as select distinct topic from interest;
create view topicx as select i.topic as name, t.description from interest_topic_distinct i left outer join topic_description t using(topic);


# --- !Downs


drop table interest;
drop table topicx;
drop view interest_topic_distinct;
drop view topicx;
