create table IF NOT EXISTS employee
(
    employee_id bigserial primary key,
    email varchar(255) not null unique,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    patronymic varchar(255) not null,
    password varchar(255) not null,
    role varchar(255) not null
);

create table if not exists units_of_measurement
(
    unit_id serial primary key,
    unit varchar(255) not null unique
);

INSERT INTO employee values (0, 'relex@gmail.com', 'Павел', 'Дуров', 'Николаевич', '$2a$10$tZj2IYB.j9IyiXEZC1ZM2OEFocHC5bPlctIeGx.03rASkR.T6eUxy', 'ADMIN');
INSERT INTO units_of_measurement values (1, 'kg'), (2, 'liter'), (3, 'unit');