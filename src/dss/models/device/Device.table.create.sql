CREATE TABLE device (
    id INTEGER PRIMARY KEY,

    name VARCHAR,
    image_url VARCHAR,
    year INTEGER,

    manufacturer_id INTEGER REFERENCES manufacturer(id)
)
