CREATE TABLE device_messaging_feature (
    device_id INTEGER REFERENCES device(id),
    messaging_feature_id REFERENCES messaging_feature(id),
    PRIMARY KEY (device_id, messaging_feature_id)
)
