
CREATE TABLE IF NOT EXISTS  employees
(
    id integer NOT NULL,
    name character varying  NOT NULL,
    email character varying   NOT NULL,
    CONSTRAINT "Employees_pkey" PRIMARY KEY (id)
);


insert into employees(id, name, email) values (1, 'emp1', 'emp1@gmail.com');
insert into employees(id, name, email) values (2, 'emp2', 'emp2@gmail.com');
insert into employees(id, name, email) values (3, 'emp3', 'emp2@gmail.com');
insert into employees(id, name, email) values (4, 'emp4', 'emp2@gmail.com');
