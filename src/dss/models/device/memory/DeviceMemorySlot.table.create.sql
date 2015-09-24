CREATE TABLE device_memory_slot (
    device_id INTEGER REFERENCES device(id),
    memory_slot_id INTEGER REFERENCES memory_slot(id),
    PRIMARY KEY (device_id, memory_slot_id)
)
