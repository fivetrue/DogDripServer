use drip;


CREATE TABLE `drip`.`users` (
  `email` VARCHAR(45) NOT NULL,
  `nickname` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `status` VARCHAR(200) NULL,
  `createdate` BIGINT(12) NULL,
  `lastconn` BIGINT(12) NULL,
  `point` INT NULL,
  `gcm` VARCHAR(200) NULL,
  `device` VARCHAR(50) NULL,
  PRIMARY KEY (`email`));

 
  