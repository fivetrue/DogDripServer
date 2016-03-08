use drip;


CREATE TABLE `drip`.`users` (
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `status` VARCHAR(200) NULL,
  `session` VARCHAR(45) NULL,
  `createdate` LONGBLOB NULL,
  `lastconn` LONGBLOB NULL,
  `point` INT NULL,
  PRIMARY KEY (`email`));

  
 
insert into admin(email, password, sessionid, lastconnection) values('fivetrue', password('qudwja123'), "", 0);  
  
  