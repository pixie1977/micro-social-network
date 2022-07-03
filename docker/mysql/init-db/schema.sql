CREATE DATABASE testdb;

CREATE USER 'admin'@'%' IDENTIFIED BY 'adminpass';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%';

CREATE USER 'user'@'%' IDENTIFIED BY 'user';
GRANT INSERT ON testdb.* TO 'user'@'%';
GRANT SELECT ON testdb.* TO 'user'@'%';
GRANT UPDATE ON testdb.* TO 'user'@'%';
GRANT DELETE ON testdb.* TO 'user'@'%';

CREATE USER 'replication'@'%' IDENTIFIED WITH mysql_native_password BY 'pass';
GRANT REPLICATION SLAVE ON *.* TO 'replication'@'%';

/* uncomment if needed */

/*

CREATE TABLE testdb.USER_TABLE (
  `id` BIGINT(20) unsigned NOT NULL,
  `login` varchar(150) NOT NULL,
  `password` varchar(300) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `gender` varchar(5) NOT NULL,
  `personal` varchar(1000) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `roles` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `first_name_last_name` (`firstName`,`lastName`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `testdb`.`USER_LINK` (
  `id` BIGINT(20) NOT NULL,
  `loginFrom` VARCHAR(150) NOT NULL,
  `loginTo` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `from_index` (`loginFrom` ASC) INVISIBLE,
  INDEX `to_index` USING BTREE (`loginTo`) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

*/