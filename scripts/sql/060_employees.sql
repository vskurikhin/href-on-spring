-----------------------------------------------------------------------------------

CREATE SEQUENCE IF NOT EXISTS employees_seq
 START WITH     207
 INCREMENT BY   1
;

CREATE TABLE IF NOT EXISTS employees (
  employee_id    BIGINT UNIQUE DEFAULT nextval('employees_seq') NOT NULL
, first_name     VARCHAR(20)
, last_name      VARCHAR(25) CONSTRAINT emp_last_name_nn NOT NULL
, email          VARCHAR(25) CONSTRAINT emp_email_nn NOT NULL
, phone_number   VARCHAR(20)
, hire_date      DATE        CONSTRAINT emp_hire_date_nn NOT NULL
, job_id         VARCHAR(10) CONSTRAINT emp_job_nn NOT NULL
, salary         REAL
, commission_pct REAL
, manager_id     BIGINT
, department_id  BIGINT
, CONSTRAINT     emp_salary_min CHECK (salary > 0) 
, CONSTRAINT     emp_email_uk   UNIQUE (email)
, CONSTRAINT     emp_emp_id_pk  PRIMARY KEY (employee_id)
);

ALTER TABLE employees ADD
  CONSTRAINT emp_dept_fk
  FOREIGN KEY (department_id)
  REFERENCES departments
;
ALTER TABLE employees ADD
  CONSTRAINT emp_job_fk
  FOREIGN KEY (job_id)
  REFERENCES jobs (job_id)
;
ALTER TABLE employees ADD
  CONSTRAINT emp_manager_fk
  FOREIGN KEY (manager_id)
  REFERENCES employees
;

ALTER TABLE departments ADD
  CONSTRAINT dept_mgr_fk
  FOREIGN KEY (manager_id)
  REFERENCES employees (employee_id)
;
