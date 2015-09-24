CREATE TABLE device_camera_primary_video (
    device_id INTEGER REFERENCES device(id),
    camera_video_id INTEGER REFERENCES camera_video(id),
    PRIMARY KEY (device_id, camera_video_id)
)
