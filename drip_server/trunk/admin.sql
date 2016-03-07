use drip;


#### 어드민 테이블 
CREATE TABLE IF NOT EXISTS `drip`.`admin` (
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `sessionid` VARCHAR(45) NOT NULL,
  `lastconnection` BIGINTs NOT NULL,
  PRIMARY KEY (`email`));
  
 
insert into admin(email, password, sessionid, lastconnection) values('fivetrue', password('qudwja123'), "", 0);  
  
  