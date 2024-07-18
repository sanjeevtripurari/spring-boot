-- DROP TABLE IF EXISTS employee;

CREATE SEQUENCE employee_id_seq;

CREATE TABLE employee (
  ID integer check (ID > 0) NOT NULL DEFAULT  nextval('employee_id_seq'),
  NAME varchar(100) NOT NULL,
  AGE int NOT NULL,
  SALARY int DEFAULT NULL,
  PRIMARY KEY (ID)
)  ;

ALTER SEQUENCE employee_id_seq OWNED BY employee.id;

