CREATE TABLE device_body (
    device_id INTEGER PRIMARY KEY REFERENCES device(id),

    width FLOAT,
    height FLOAT,
    depth FLOAT,

    weight FLOAT,

    sim_type_id INTEGER REFERENCES sim_type(id)
)
