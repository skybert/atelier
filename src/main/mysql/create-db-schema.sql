
-- create db and assign rights to it
create database atelierdb character set utf8 collate utf8_general_ci;
grant all on atelierdb.* to atelieruser@'%' identified by 'atelierpassword';
grant all on atelierdb.* to atelieruser@'localhost' identified by 'atelierpassword';
use atelierdb;
