CREATE TABLE device_camera (
    device_id INTEGER PRIMARY KEY REFERENCES device(id),

    primary_mp INTEGER,
    primary_width INTEGER,
    primary_height INTEGER,

    secondary_has BOOLEAN,
    secondary_mp INTEGER,
    secondary_video_id INTEGER REFERENCES camera_video(id)
)
