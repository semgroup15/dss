CREATE TABLE device_camera_primary_feature (
    device_id INTEGER REFERENCES device(id),
    camera_feature_id INTEGER REFERENCES camera_feature(id),
    PRIMARY KEY (device_id, camera_feature_id)
)
