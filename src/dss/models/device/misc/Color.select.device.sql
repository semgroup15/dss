SELECT *
FROM color
WHERE color.id IN (
    SELECT color_id
    FROM device_color
    WHERE device_id = ?
)
