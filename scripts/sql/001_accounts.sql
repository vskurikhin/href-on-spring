------------------------------------------------------------------------
--- Prompt ******  Creating ACCOUNTS table ....

CREATE TABLE IF NOT EXISTS accounts (
  username    VARCHAR(32) UNIQUE CONSTRAINT country_id_nn NOT NULL
, password    VARCHAR(255)
, active      BOOLEAN
);
