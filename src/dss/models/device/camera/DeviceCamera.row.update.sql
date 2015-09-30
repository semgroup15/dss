UPDATE device_camera
SET primary_mp = ?, primary_width = ?, primary_height = ?,
    secondary_has = ?, secondary_mp = ?, secondary_video_id = ?
WHERE device_id = ?
