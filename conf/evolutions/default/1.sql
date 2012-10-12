
# --- !Ups

create table user (
  id                        integer primary key AUTOINCREMENT,
  full_name                 varchar(255),
  email                     varchar(255),
  city                      varchar(255),
  google_id					varchar(255))
;

# --- !Downs

PRAGMA foreign_keys = OFF;

drop table user;

PRAGMA foreign_keys = ON;

