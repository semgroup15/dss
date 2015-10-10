SELECT *
FROM network_technology
WHERE network_technology.id IN (
    SELECT network_technology_id
    FROM device_network_technology
    WHERE device_id = ?
)
