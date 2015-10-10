SELECT *
FROM messaging_feature
WHERE messaging_feature.id IN (
    SELECT messaging_feature_id
    FROM device_messaging_feature
    WHERE device_id = ?
)
