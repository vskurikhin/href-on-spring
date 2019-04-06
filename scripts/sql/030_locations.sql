-----------------------------------------------------------------------------------
--- Create the LOCATIONS table to hold address information for company departments.
--- HR.DEPARTMENTS has a foreign key to this table.

---     Useful for any subsequent addition of rows to locations table
---     Starts with 3300

CREATE SEQUENCE IF NOT EXISTS locations_seq
  START WITH     3300
  INCREMENT BY   100
  MAXVALUE       9900
;

-- Prompt ******  Creating LOCATIONS table ....

CREATE TABLE IF NOT EXISTS locations (
  location_id    BIGINT UNIQUE DEFAULT nextval('locations_seq') NOT NULL
, street_address VARCHAR(40)
, postal_code    VARCHAR(12)
, city           VARCHAR(30) CONSTRAINT loc_city_nn  NOT NULL
, state_province VARCHAR(25)
, country_id     CHAR(2)
);

ALTER TABLE locations ADD 
  CONSTRAINT location_c_id_pk
  PRIMARY KEY (location_id)
;
ALTER TABLE locations ADD 
  CONSTRAINT loc_c_id_fk 
  FOREIGN KEY (country_id) 
  REFERENCES countries(country_id)
;
