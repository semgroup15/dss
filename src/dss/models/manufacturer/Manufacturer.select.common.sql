SELECT * FROM (
    SELECT manufacturer.*, COUNT(device.id) AS count
    FROM manufacturer
    JOIN device ON device.manufacturer_id = manufacturer.id
    GROUP BY manufacturer.id
    ORDER BY count DESC
    LIMIT 30
) AS manufacturer ORDER BY manufacturer.name