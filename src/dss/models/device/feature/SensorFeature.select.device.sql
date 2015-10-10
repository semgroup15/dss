SELECT *
FROM sensor_feature
WHERE sensor_feature.id IN (
    SELECT sensor_feature_id
    FROM device_sensor_feature
    WHERE device_id = ?
)
