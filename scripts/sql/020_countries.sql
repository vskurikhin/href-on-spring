------------------------------------------------------------------------
--- Create the COUNTRIES table to hold country information for customers
--- and company locations.
--- OE.CUSTOMERS table and HR.LOCATIONS have a foreign key to this table.

-- Prompt ******  Creating COUNTRIES table ....

CREATE TABLE IF NOT EXISTS countries (
  country_id      CHAR(2) UNIQUE CONSTRAINT country_id_nn NOT NULL
, country_name    VARCHAR(40)
, region_id       BIGINT
, CONSTRAINT      country_c_id_pk PRIMARY KEY (country_id)
);

ALTER TABLE countries ADD
   CONSTRAINT countr_reg_fk
   FOREIGN KEY (region_id)
   REFERENCES regions(region_id)
;
