# --- !Ups
create table form_token (
	user integer not null,
	form varchar(255) not null,
	token varchar(255) not null,
	foreign key(user) references user(id),
	primary key(user, form, token)
);

# --- !Downs
drop table form_token;
