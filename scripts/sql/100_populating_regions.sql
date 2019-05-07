-- Prompt ******  Populating REGIONS table ....

INSERT INTO regions VALUES
  (1, 'Europe')
, (2, 'Americas')
, (3, 'Asia')
, (4, 'Middle East and Africa')
;

ALTER SEQUENCE regions_region_id_seq RESTART WITH 5;
