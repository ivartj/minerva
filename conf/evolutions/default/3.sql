# Update User
 
# --- !Ups
ALTER TABLE User ADD image_url varchar(255);
 
# --- !Downs
ALTER TABLE User DROP image_url;