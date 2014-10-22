DROP DATABASE userdb;
CREATE DATABASE userdb CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE userdb.users (
username varchar(20) NOT NULL PRIMARY KEY,
password varchar(20) NOT NULL
);
CREATE TABLE userdb.roles (
rolename varchar(20) NOT NULL PRIMARY KEY
);
CREATE TABLE userdb.users_roles (
username varchar(20) NOT NULL,
rolename varchar(20) NOT NULL,
PRIMARY KEY (username, rolename),
CONSTRAINT users_roles_fk1 FOREIGN KEY (username) REFERENCES userdb.users (username),
CONSTRAINT users_roles_fk2 FOREIGN KEY (rolename) REFERENCES userdb.roles (rolename)
);

INSERT INTO userdb.users (username, password) VALUES ('corp\\qjafcunuas', 'toto');
INSERT INTO userdb.roles (rolename) VALUES ('ADMIN');
INSERT INTO userdb.roles (rolename) VALUES ('GUEST');
INSERT INTO userdb.users_roles (username, rolename) VALUES ('corp\\qjafcunuas', 'ADMIN');
INSERT INTO userdb.users_roles (username, rolename) VALUES ('corp\\qjafcunuas', 'GUEST');
select * from userdb.users;
select * from userdb.users_roles;