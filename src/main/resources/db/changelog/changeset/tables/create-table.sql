create table IF NOT EXISTS employee
(
    employee_id bigint primary key,
    email varchar(255) not null unique,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    patronymic varchar(255) not null,
    password varchar(255) not null,
    role smallint not null
);

INSERT INTO employee values (1, 'relex@gmail.com', 'Павел', 'Дуров', 'Николаевич', '$2a$10$tZj2IYB.j9IyiXEZC1ZM2OEFocHC5bPlctIeGx.03rASkR.T6eUxy', 0);