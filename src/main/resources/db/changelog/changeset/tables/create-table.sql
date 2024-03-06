create table IF NOT EXISTS role
(
    role_id integer
        primary key,
    name varchar(30) not null
        unique
);

INSERT  into role values (1,'ADMIN'),(2,'EMPLOYEE');