--
--  Creates a new mysql database for use with Atelier
--
--  Last edited by: $Author: torstein $ $Date: 2007/12/29 18:00:39 $
--  Revision:       $Revision: 1.4 $

-- create db and assign rights to it
-- create database atelierdb;
create database atelierdb character set utf8 collate utf8_general_ci;
grant all on atelierdb.* to atelieruser@'%' identified by 'atelierpassword';
grant all on atelierdb.* to atelieruser@'localhost' identified by 'atelierpassword';
use atelierdb;

-- create the tables
source tables.sql;
source fulltext-table.sql

-- add constants
source constants.sql;

-- add constraints
source constraints.sql;

-- add indexes
source indexes.sql;

-- add dummy data
-- source dummy-data.sql




