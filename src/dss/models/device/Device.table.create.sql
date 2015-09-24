CREATE TABLE device (
    id INTEGER PRIMARY KEY,

    name VARCHAR,
    year INTEGER,

    manufacturer_id INTEGER REFERENCES manufacturer(id)
)
