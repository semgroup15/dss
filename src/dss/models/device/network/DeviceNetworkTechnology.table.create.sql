CREATE TABLE device_network_technology (
    device_id INTEGER REFERENCES device(id),
    network_technology_id REFERENCES network_technology(id),
    PRIMARY KEY (device_id, network_technology_id)
)
