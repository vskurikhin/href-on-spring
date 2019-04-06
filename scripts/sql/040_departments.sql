-----------------------------------------------------------------------------------
--- Create the LOCATIONS table to hold address information for company departments.
--- HR.DEPARTMENTS has a foreign key to this table.

CREATE SEQUENCE IF NOT EXISTS departments_seq
  START WITH     280
  INCREMENT BY   10
  MAXVALUE       9990
;

-- Prompt ******  Creating LOCATIONS table ....

CREATE TABLE departments (
  department_id    BIGINT UNIQUE DEFAULT nextval('departments_seq') NOT NULL
, department_name  VARCHAR(30) CONSTRAINT dept_name_nn  NOT NULL
, manager_id       BIGINT
, location_id      BIGINT
, CONSTRAINT       dept_id_pk PRIMARY KEY (department_id)
);

ALTER TABLE departments ADD 
  CONSTRAINT dept_loc_fk 
  FOREIGN KEY (location_id)
  REFERENCES locations (location_id)
;
