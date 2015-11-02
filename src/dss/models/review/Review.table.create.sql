CREATE TABLE review (
    id INTEGER PRIMARY KEY,
    device_id INTEGER REFERENCES device(id),
    responsiveness INTEGER,
    screen INTEGER,
    battery INTEGER,
    text VARCHAR,

)