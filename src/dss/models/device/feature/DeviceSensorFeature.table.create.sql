CREATE TABLE device_sensor_feature (
    device_id INTEGER REFERENCES device(id),
    sensor_feature_id REFERENCES sensor_feature(id),
    PRIMARY KEY (device_id, sensor_feature_id)
)
