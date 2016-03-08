use drip;


CREATE TABLE `drip`.`users` (
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `status` VARCHAR(200) NULL,
  `session` VARCHAR(45) NULL,
  `createdate` BIGINT(12) NULL,
  `lastconn` BIGINT(12) NULL,
  `point` INT NULL,
  PRIMARY KEY (`email`));

 
  