create table customer
(
 id INTEGER AUTO_INCREMENT,
 first_name VARCHAR(200) ,
 last_name VARCHAR(200) ,
 creation_date DATETIME,
 email_address VARCHAR(60) ,
 address VARCHAR(255) ,
 post_code VARCHAR(255) ,
 home_phone VARCHAR(20) ,
 mobile_phone VARCHAR(20) ,
 work_phone VARCHAR(20) ,
 birth_date DATETIME,
 old_customer_id VARCHAR(255),
 old_archive_id VARCHAR(255),
 constraint pk_customer primary key(id)
) ENGINE=InnoDB;

create table user
(
 id INTEGER AUTO_INCREMENT,
 user_name VARCHAR(40) ,
 first_name VARCHAR(40) ,
 last_name VARCHAR(40) ,
 creation_date DATETIME,
 email_address VARCHAR(60) ,
 address VARCHAR(255) ,
 post_code VARCHAR(255) ,
 home_phone VARCHAR(20) ,
 mobile_phone VARCHAR(20) ,
 work_phone VARCHAR(20) ,
 birth_date DATETIME,
 encrypted_password VARBINARY(16) ,
 constraint pk_user primary key(id)
) ENGINE=InnoDB;

create table customer_order
(
 id INTEGER AUTO_INCREMENT,
 customer_id INTEGER,
 payment_type_id INTEGER,
 order_status_id INTEGER,
 creation_date DATETIME,
 updated_date DATETIME,
 delivery_date DATETIME,
 paid_amount DECIMAL (9,2),
 comment VARCHAR(255),
 newspaper_allowed INTEGER not null,
 marketing_allowed INTEGER not null,
 constraint pk_order primary key(id)
) ENGINE=InnoDB;

create table invoice
(
 id INTEGER AUTO_INCREMENT,
 order_id INTEGER,
 creation_date DATETIME,
 due_date DATETIME,
 tax_included INTEGER not null,
 constraint pk_order primary key(id)
) ENGINE=InnoDB;

create table order_item
(
 id INTEGER AUTO_INCREMENT,
 creation_date DATETIME,
 order_id INTEGER,
 product_id INTEGER,
 number_of_items INTEGER,
 discount DECIMAL (3,1),
 total_amount DECIMAL (9,2),
 comment VARCHAR(255),
 constraint pk_order primary key(id)
) ENGINE=InnoDB;

-- don't use this
create table order_comment
(
 id INTEGER AUTO_INCREMENT,
 comment VARCHAR(255) not null,
 creation_date DATETIME ,
 user_id INTEGER not null,
 order_id INTEGER not null,
 constraint pk_order_comment primary key(id)
) ENGINE=InnoDB;

create table order_status
(
 id INTEGER AUTO_INCREMENT,
 name VARCHAR(40) not null,
 description VARCHAR(40) ,
 constraint pk_order_status primary key(id),
 constraint unique_order_status_name unique(name)
) ENGINE=InnoDB;

create table product_type
(
 id INTEGER AUTO_INCREMENT,
 name VARCHAR(40) not null,
 description VARCHAR(40) ,
 constraint pk_product_type primary key(id),
 constraint unique_product_type_name unique(name)
) ENGINE=InnoDB;

create table payment_type
(
 id INTEGER AUTO_INCREMENT,
 name VARCHAR(40) not null,
 description VARCHAR(40) ,
 constraint pk_payment_type primary key(id),
 constraint unique_payment_type_name unique(name)
) ENGINE=InnoDB;

create table post_place
(
 id INTEGER AUTO_INCREMENT,
 post_code VARCHAR(40) not null,
 post_place VARCHAR(40) not null,
 constraint pk_post_place primary key(id),
 constraint unique_post_code unique(post_code)
) ENGINE=InnoDB;

create table product
(
 id INTEGER AUTO_INCREMENT,
 name VARCHAR(40) not null,
 creation_date DATETIME,
 description VARCHAR(40),
 price DECIMAL(9,2),
 production_time INTEGER,
 product_type_id INTEGER not null,
 constraint pk_product primary key(id)
) ENGINE=InnoDB;


