# Update User
 
# --- !Ups
alter table user add image_url varchar(255);
 
# --- !Downs
alter table user drop image_url;
