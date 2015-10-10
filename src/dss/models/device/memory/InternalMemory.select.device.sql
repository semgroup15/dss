SELECT *
FROM internal_memory
WHERE internal_memory.id IN (
    SELECT internal_memory_id
    FROM device_internal_memory
    WHERE device_id = ?
)
