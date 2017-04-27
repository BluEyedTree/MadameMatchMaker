
DROP TABLE IF EXISTS Activity;

CREATE TABLE Activity (
  ID int(11) NOT NULL,
  ActivityDescription varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID)
);


INSERT INTO Activity VALUES (1,'Snuggle'),(2,'WalkToTheBay');




DROP TABLE IF EXISTS Interest;

CREATE TABLE Interest (
  IDp INTEGER PRIMARY KEY AUTOINCREMENT,
  Emailp varchar(45) DEFAULT NULL,
  InterestEmail varchar(45) DEFAULT NULL,
  ActivityID int(11) DEFAULT NULL
  
);



INSERT INTO Interest VALUES (1,'Thomas.Bekman12@ncf.edu','Cora.Coleman14@ncf.edu',1),(2,'Thomas.Bekman12@ncf.edu','Cora.Coleman14@ncf.edu',2),(3,'Cora.Coleman14@ncf.edu','Thomas.Bekman12@ncf.edu',2);




DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
  Email varchar(45) NOT NULL,
  Username varchar(45) DEFAULT NULL,
  Password varchar(45) DEFAULT NULL,
  PRIMARY KEY (Email)
);

INSERT INTO Users VALUES ('Cora.Coleman14@ncf.edu','Cora12','Cora'),('loner@ncf.edu','123','123'),('Thomas.Bekman12@ncf.edu','Tom12','Tom');

