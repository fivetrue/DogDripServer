use drip;


CREATE TABLE `drip`.`users` (
  `email` VARCHAR(45) NOT NULL,
  `nickname` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `imageurl` VARCHAR(100) NOT NULL,
  `createdate` BIGINT(12) NULL,
  `lastconn` BIGINT(12) NULL,
  `point` INT NULL,
  PRIMARY KEY (`email`));

 
insert into users (email,nickname,password,imageurl,createdate,lastconn,point) 
values ('admin','admin',password('emflqadmin'), 'http://52.76.24.8:8080/dogdic/resource/images/default_user', 1457542902710,0,0);