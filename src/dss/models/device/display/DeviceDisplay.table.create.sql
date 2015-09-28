CREATE TABLE device_display (
    device_id INTEGER PRIMARY KEY REFERENCES device(id),

    size FLOAT,
    ratio FLOAT,

    width INTEGER,
    height INTEGER,
    density INTEGER,

    multitouch BOOLEAN,

    type_id INTEGER REFERENCES display_type(id),
    protection_id INTEGER REFERENCES display_protection(id)
)
