CREATE TABLE device_battery (
    device_id INTEGER PRIMARY KEY REFERENCES device(id),

    descr VARCHAR,

    sleep INTEGER,
    talk INTEGER,
    music INTEGER,
    rating INTEGER
)
