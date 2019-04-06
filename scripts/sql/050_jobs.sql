-----------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS jobs (
  job_id         VARCHAR(10) UNIQUE
, job_title      VARCHAR(35) CONSTRAINT job_title_nn NOT NULL
, min_salary     REAL
, max_salary     REAL
, CONSTRAINT     job_id_pk PRIMARY KEY (job_id)
);
