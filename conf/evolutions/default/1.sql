
# --- !Ups

PRAGMA synchronous = OFF;

create table user (
  id                        integer primary key AUTOINCREMENT,
  full_name                 varchar(255),
  first_name				varchar(50),
  last_name					varchar(50),
  age						   integer,
  phone						   varchar(8),
  email             varchar(255),
  address           varchar(255),
  city              varchar(255),
  country           varchar(255),
  google_id					varchar(255),
  yahoo_id					varchar(255))
;

# --- !Downs

PRAGMA foreign_keys = OFF;

drop table user;

PRAGMA foreign_keys = ON;

