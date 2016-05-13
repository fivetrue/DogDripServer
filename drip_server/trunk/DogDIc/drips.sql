use drip;


CREATE TABLE `drip`.`drips` (
  `dripid` INT AUTO_INCREMENT,
  `drip` VARCHAR(512) NOT NULL,
  `dripimage` VARCHAR(512),
  `userid` VARCHAR(45) NOT NULL,
  `createdate` BIGINT(12) NULL,
  `heartcount` INT NULL,
  PRIMARY KEY (`id`));

  
  
  