UPDATE device_platform
SET platform_os_id = ?, platform_os_current_version = ?,
    platform_os_upgrade_version = ?,
    platform_chipset_id = ?, platform_gpu_id = ?,
    platform_cpu_type_id = ?, platform_cpu_freq = ?, platform_cpu_raw = ?
WHERE device_id = ?
