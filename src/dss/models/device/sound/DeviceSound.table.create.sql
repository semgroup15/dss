CREATE TABLE device_sound (
    device_id INTEGER PRIMARY KEY REFERENCES device(id),

    loudspeaker BOOLEAN,
    jack35 BOOLEAN
)
