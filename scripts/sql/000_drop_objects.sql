------------------------------------------------------------------------
SET client_min_messages TO WARNING;

DROP TABLE IF EXISTS job_history CASCADE;

DROP SEQUENCE IF EXISTS employees_seq CASCADE;
DROP TABLE IF EXISTS employees CASCADE;

DROP TABLE IF EXISTS jobs CASCADE;

DROP SEQUENCE IF EXISTS departments_seq CASCADE;
DROP TABLE IF EXISTS departments CASCADE;

DROP SEQUENCE IF EXISTS locations_seq CASCADE;
DROP TABLE IF EXISTS locations CASCADE;

DROP TABLE IF EXISTS countries CASCADE;

DROP TABLE IF EXISTS regions CASCADE;

SET client_min_messages TO NOTICE;
------------------------------------------------------------------------
