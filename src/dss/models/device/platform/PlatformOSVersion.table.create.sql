CREATE TABLE platform_os_version (
    id INTEGER PRIMARY KEY,
    name VARCHAR,
    os_id INTEGER REFERENCES platform_os(id)
)
