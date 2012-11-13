# Update User
 
# --- !Ups
alter table user add image_url varchar(255);
 
# --- !downs
alter table user drop image_url;
