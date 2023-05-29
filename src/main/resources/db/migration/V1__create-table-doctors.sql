create table doctors(
	id bigint not null auto_increment,
	name varchar(50) not null,
	email varchar(50) not null unique,
	DNI varchar(10) not null unique,
	specialization varchar(50) not null,
	street varchar(15) not null,
	town varchar(15) not null,
	state varchar(15) not null,
	number varchar(15) not null,
	
	primary key(id)

)