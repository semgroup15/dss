SELECT *
FROM camera_video
WHERE camera_video.id IN (
    SELECT camera_video_id
    FROM device_camera_primary_video
    WHERE device_id = ?
)
