SELECT *
FROM memory_slot
WHERE memory_slot.id IN (
    SELECT memory_slot_id
    FROM device_memory_slot
    WHERE device_id = ?
)
