drop table if exists customer;
drop table if exists history;

create table customer (
                          email varchar(255) not null,
                          password varchar(255),
                          primary key (email)
) engine=InnoDB;

create table history (
                         id bigint not null auto_increment,
                         create_date_time datetime(6),
                         is_deleted bit not null,
                         memo varchar(255),
                         money bigint not null,
                         email varchar(255),
                         primary key (id)
) engine=InnoDB;