\i 000_drop_objects.sql
\i 010_regions.sql
\i 020_countries.sql
\i 030_locations.sql
\i 040_departments.sql
\i 050_jobs.sql
\i 060_employees.sql
\i 070_job_history.sql
\i 080_emp_details_view.sql

\i 100_populating_regions.sql
\i 110_populating_countries.sql
\i 120_populating_locations.sql

ALTER USER dbuser WITH SUPERUSER;
ALTER TABLE departments DISABLE TRIGGER ALL;

\i 130_populating_departments.sql
\i 140_populating_jobs.sql
\i 150_populating_employees.sql

ALTER TABLE departments ENABLE TRIGGER ALL;
ALTER USER dbuser WITH NOSUPERUSER;
