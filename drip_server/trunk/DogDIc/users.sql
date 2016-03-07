use drip;


CREATE TABLE `drip`.`users` (
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `status` JSON NULL,
  `session` VARCHAR(45) NULL,
  `createdate` MEDIUMINT(10) NULL,
  `lastconn` MEDIUMINT(10) NULL,
  `point` INT NULL,
  PRIMARY KEY (`email`));

  
 
insert into admin(email, password, sessionid, lastconnection) values('fivetrue', password('qudwja123'), "", 0);  
  
  