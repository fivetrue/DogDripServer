use drip;


CREATE TABLE `drip`.`users` (
  `userid` VARCHAR(45) NOT NULL,
  `nickname` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `userimage` VARCHAR(100) NOT NULL,
  `gcm` VARCHAR(300) NOT NULL,
  `joindate` BIGINT(12) NULL,
  `lastconn` BIGINT(12) NULL,
  `point` INT NULL,
  PRIMARY KEY (`userid`));

 
insert into users (email,nickname,password,imageurl,createdate,lastconn,point,gcm) 
values ('admin','admin',password('emflqadmin'), 'http://52.76.24.8:8080/dogdic/resource/images/default_user', 1457542902710,0,0,'');