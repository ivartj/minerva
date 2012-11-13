
# --- !Ups

create table user (
  id                        integer primary key auto_increment,
  cookie_identifier			varchar(255),
  full_name                 varchar(255),
  first_name				varchar(50),
  last_name					varchar(50),
  age						integer,
  phone						varchar(8),
  email             		varchar(255),
  alternative_Email			varchar(255),
  address           		varchar(255),
  city              		varchar(255),
  country           		varchar(255),
  image_url					varchar(255),
  google_id					varchar(255),
  yahoo_id					varchar(255))
;

# --- !Downs

drop table user;

