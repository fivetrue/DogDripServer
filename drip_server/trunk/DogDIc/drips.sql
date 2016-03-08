use drip;


CREATE TABLE `drip`.`drips` (
  `id` INT AUTO_INCREMENT,
  `drip` VARCHAR(512) NOT NULL,
  `author` VARCHAR(45) NOT NULL,
  `createdate` LONGBLOB NULL,
  `heartcount` INT NULL,
  PRIMARY KEY (`id`));

  
  
  