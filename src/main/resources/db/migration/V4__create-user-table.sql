create table user(
	id bigint not null auto_increment,
	username varchar(100) not null,
	psw varchar(300) not null,
	
	primary key(id)
)