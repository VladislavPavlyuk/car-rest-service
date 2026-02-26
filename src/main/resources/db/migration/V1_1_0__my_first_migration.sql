CREATE TABLE IF NOT EXISTS category (
    id SERIAL PRIMARY KEY,
    name character varying(100)
);

CREATE TABLE IF NOT EXISTS maker (
    id SERIAL PRIMARY KEY,
    name character varying(100)
);

CREATE TABLE IF NOT EXISTS model (
    id SERIAL PRIMARY KEY,
    name character varying(100),
    maker_id integer,
    FOREIGN KEY (maker_id) REFERENCES maker (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS information (
    id SERIAL PRIMARY KEY,
    objectid character varying(100),
    "year" date,
    model_id integer,
    FOREIGN KEY (model_id) REFERENCES model (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS modelcategory
(
    id SERIAL PRIMARY KEY,
    category_id integer,
    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE,
    model_id integer,
    FOREIGN KEY (model_id) REFERENCES model (id) ON DELETE CASCADE
);