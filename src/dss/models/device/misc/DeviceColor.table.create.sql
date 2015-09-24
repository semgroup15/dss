CREATE TABLE device_color (
    device_id INTEGER REFERENCES device(id),
    color_id INTEGER REFERENCES color(id),
    PRIMARY KEY (device_id, color_id)
)
