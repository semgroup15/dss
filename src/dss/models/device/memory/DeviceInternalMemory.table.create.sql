CREATE TABLE device_internal_memory (
    device_id INTEGER REFERENCES device(id),
    internal_memory_id INTEGER REFERENCES internal_memory(id),
    PRIMARY KEY (device_id, internal_memory_id)
)
