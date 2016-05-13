use drip;


CREATE TABLE `drip`.`reply` (
  `replyid` INT AUTO_INCREMENT,
  `dripid` INT NOT NULL,
  `comment` VARCHAR(200) NOT NULL,
  `userid` VARCHAR(45) NOT NULL,
  `replydate` BIGINT(12) NULL,
  PRIMARY KEY (`replyid`));

 