CREATE TABLE device_platform (
    device_id INTEGER PRIMARY KEY REFERENCES device(id),

    platform_os_id INTEGER REFERENCES platform_os(id),
    platform_os_current_version INTEGER REFERENCES platform_os_version(id),
    platform_os_upgrade_version INTEGER REFERENCES platform_os_version(id),

    platform_chipset_id INTEGER REFERENCES platform_chipset(id),
    platform_gpu_id INTEGER REFERENCES platform_gpu(id),

    platform_cpu_type_id INTEGER REFERENCES platform_cpu_type(id),
    platform_cpu_freq FLOAT,
    platform_cpu_raw VARCHAR
)
