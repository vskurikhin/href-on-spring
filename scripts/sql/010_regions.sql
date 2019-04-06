------------------------------------------------------------------------
--- Create the REGIONS table to hold region information for locations
--- HR.LOCATIONS table has a foreign key to this table.

--- Prompt ******  Creating REGIONS table ....

CREATE TABLE IF NOT EXISTS regions (
  region_id SERIAL PRIMARY KEY
, region_name VARCHAR(25)
);
