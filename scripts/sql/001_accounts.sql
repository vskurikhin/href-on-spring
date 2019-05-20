------------------------------------------------------------------------
--- Prompt ******  Creating ACCOUNTS table ....

CREATE TABLE IF NOT EXISTS accounts (
  username    VARCHAR(32) UNIQUE CONSTRAINT country_id_nn NOT NULL
, password    VARCHAR(255)
, active      BOOLEAN
);

INSERT INTO accounts
  VALUES (
    'user',
    '{bcrypt}$2a$10$HdSlY4VfCuJmHPiWl526qu1/ib.83OSpjemT2MIScpXN5WqX9N0.i',
    TRUE
);
