SELECT *
FROM camera_feature
WHERE camera_feature.id IN (
    SELECT camera_feature_id
    FROM device_camera_primary_feature
    WHERE device_id = ?
)
