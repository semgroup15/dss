CREATE TABLE platform_os_version (
    id INTEGER PRIMARY KEY,
    os_id INTEGER REFERENCES platform_os(id),
    name VARCHAR
)
