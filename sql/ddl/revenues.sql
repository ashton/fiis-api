-- DDL generated by Postico 1.5.17
-- Not all database features are supported. Do not use for backup.

-- Table Definition ----------------------------------------------

CREATE TABLE revenues (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    code text NOT NULL REFERENCES funds(code),
    base_price numeric(15,5),
    dy numeric(15,5),
    value numeric(15,5) NOT NULL,
    date date NOT NULL
);

-- Indices -------------------------------------------------------

CREATE UNIQUE INDEX revenues_pkey ON revenues(id uuid_ops);
CREATE UNIQUE INDEX revenues_code_date_idx ON revenues(code text_ops,date date_ops);