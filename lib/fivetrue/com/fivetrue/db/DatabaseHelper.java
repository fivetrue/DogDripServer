package com.fivetrue.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseHelper <T> {
	
	private static final String MYSQL_PORT = "3306";
	private static final String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	private static final String MYSQL_PARAMS = "useEncoding=true&characterEncoding=UTF-8";
	
	public static interface ResultSetListener{
		void onReceived(ResultSet rs);
	}
	
	private String mDbUrl = null;
	private String mDbUserId = null;
	private String mDbUserPass = null;
	
	static{
		try {
			Class.forName(MYSQL_DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                      
	}
	
	public DatabaseHelper(String dbServer, String dbName, String id, String pass){
		
		if(dbServer == null || dbName == null || id == null || pass == null){
			throw new IllegalArgumentException("Parameters must be not null");
		}
		
		mDbUrl = "jdbc:mysql://" + dbServer +  ":" + MYSQL_PORT + "/" + dbName + "?" + MYSQL_PARAMS;        
		mDbUserId = id;
		mDbUserPass = pass;
	}
	
	public ArrayList<T> rawSelectQuery(String query, Class <? extends DatabaseObject> cls){
		ArrayList<T> datas = new ArrayList<T>();
		Connection conn = connectDatabase();
		if(query != null && conn != null){
			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()){
					DatabaseObject obj = null;
					try {
						obj = cls.newInstance();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					obj.setObject(rs);
					datas.add((T)obj);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally{
				disconnectDatabase(conn);
			}
		}
		return datas;
	}
	
	public Connection connectDatabase(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(mDbUrl, mDbUserId, mDbUserPass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public void disconnectDatabase(Connection conn){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
use movie;
CREATE TABLE `director` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `debut` mediumtext,
  `nationality` char(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `genres` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grade` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `member` (
  `birth` mediumtext,
  `gender` int(1) DEFAULT NULL,
  `address` varchar(90) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `level` int(11) DEFAULT NULL,
  `point` int(11) DEFAULT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `production` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `movie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `date` mediumtext,
  `grade` int(11) DEFAULT NULL,
  `genre` int(11) DEFAULT NULL,
  `director` int(11) DEFAULT NULL,
  `production` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `grade_idx` (`grade`),
  KEY `genre_idx` (`genre`),
  KEY `director_idx` (`director`),
  KEY `production_idx` (`production`),
  CONSTRAINT `director` FOREIGN KEY (`director`) REFERENCES `director` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `genre` FOREIGN KEY (`genre`) REFERENCES `genres` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `grade` FOREIGN KEY (`grade`) REFERENCES `director` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `production` FOREIGN KEY (`production`) REFERENCES `production` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `userStatus` (
  `email` varchar(45) NOT NULL,
  `sessionId` varchar(45) NOT NULL,
  `timeStamp` mediumtext,
  `deviceId` varchar(45) DEFAULT NULL,
  `status` int(1) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `movie`.`board` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `content` VARCHAR(200) NULL,
  `author` VARCHAR(45) NOT NULL,
  `timestamp` MEDIUMTEXT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`no`),
  INDEX `user_idx` (`author` ASC),
  CONSTRAINT `author`
    FOREIGN KEY (`author`)
    REFERENCES `movie`.`member` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
    CREATE TABLE `movie`.`reply` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(45) NOT NULL,
  `author` VARCHAR(45) NOT NULL,
  `timestamp` MEDIUMTEXT NOT NULL,
  `boardno` INT NOT NULL,
  PRIMARY KEY (`no`),
  CONSTRAINT `boardno`
    FOREIGN KEY (`no`)
    REFERENCES `movie`.`board` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)ENGINE=InnoDB DEFAULT CHARSET=utf8;


	 */

}
