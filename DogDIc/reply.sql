use drip;


CREATE TABLE `drip`.`reply` (
  `id` INT AUTO_INCREMENT,
  `dripid` INT NOT NULL,
  `comment` VARCHAR(200) NOT NULL,
  `author` VARCHAR(45) NOT NULL,
  `createdate` BIGINT(12) NULL,
  PRIMARY KEY (`id`));

 