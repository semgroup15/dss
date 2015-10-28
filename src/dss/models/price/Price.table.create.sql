CREATE TABLE price (
    id INTEGER PRIMARY KEY,
    device_id INTEGER REFERENCES device(id),
    retailer VARCHAR,
    cost FLOAT
)