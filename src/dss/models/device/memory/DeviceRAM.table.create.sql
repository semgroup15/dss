CREATE TABLE device_ram (
    device_id INTEGER PRIMARY KEY REFERENCES device(id),

    size INTEGER
)
