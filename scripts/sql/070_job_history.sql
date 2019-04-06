-----------------------------------------------------------------------------------

CREATE TABLE job_history (
  employee_id   BIGINT      CONSTRAINT jhist_employee_nn NOT NULL
, start_date    DATE        CONSTRAINT jhist_start_date_nn NOT NULL
, end_date      DATE        CONSTRAINT jhist_end_date_nn NOT NULL
, job_id        VARCHAR(10) CONSTRAINT jhist_job_nn NOT NULL
, department_id BIGINT
, CONSTRAINT    jhist_date_interval CHECK (end_date > start_date)
, CONSTRAINT    jhist_emp_id_st_date_pk PRIMARY KEY (employee_id, start_date)
);

ALTER TABLE job_history ADD
  CONSTRAINT jhist_job_fk
  FOREIGN KEY (job_id)
  REFERENCES jobs
;
ALTER TABLE job_history ADD
  CONSTRAINT jhist_emp_fk
  FOREIGN KEY (employee_id)
  REFERENCES employees
;
ALTER TABLE job_history ADD
  CONSTRAINT jhist_dept_fk
  FOREIGN KEY (department_id)
  REFERENCES departments
;
