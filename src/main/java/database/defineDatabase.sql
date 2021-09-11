create database procedures_db;

create table procedures_db.procedures_type(
	id int not null auto_increment,
	name varchar(30) not null,
  	porcent decimal(5,2) not null,
  	primary key(id)
);

create table procedures_db.service_procedure(
	id int not null auto_increment,
  	performedIn date not null,
  	client varchar(45) not null,
  	price decimal(6,2) not null,
  	typeId int not null,
  	received decimal(6,2) not null,
  	registered_date timestamp not null default current_timestamp,
  	primary key(id),
  	foreign key(typeId) references procedures_type(id)
);
