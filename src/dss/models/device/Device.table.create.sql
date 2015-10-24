CREATE TABLE device (
    id INTEGER PRIMARY KEY,

    name VARCHAR,
    image_url VARCHAR,
    year INTEGER,

    manufacturer_id INTEGER REFERENCES manufacturer(id),

    battery_sleep INTEGER,
    battery_talk INTEGER,
    battery_music INTEGER,

    body_width FLOAT,
    body_height FLOAT,
    body_depth FLOAT,
    body_weight FLOAT,

    camera_primary_mp INTEGER,
    camera_primary_width INTEGER,
    camera_primary_height INTEGER,

    camera_secondary_has BOOLEAN,
    camera_secondary_mp INTEGER,

    com_wlan BOOLEAN,
    com_bluetooth BOOLEAN,
    com_gps BOOLEAN,
    com_radio BOOLEAN,
    com_usb BOOLEAN,
    com_nfc BOOLEAN,

    sensor_accelerometer BOOLEAN,
    sensor_barometer BOOLEAN,
    sensor_compass BOOLEAN,
    sensor_gyro BOOLEAN,
    sensor_magnetometer BOOLEAN,
    sensor_proximity BOOLEAN,

    memory_ram_size INTEGER,
    memory_internal_size INTEGER,

    memory_sd BOOLEAN,
    memory_micro_sd BOOLEAN,
    memory_micro_sdhc BOOLEAN,
    memory_mmc BOOLEAN,
    memory_mmc_mobile BOOLEAN,

    network_gsm BOOLEAN,
    network_umts BOOLEAN,
    network_hspa BOOLEAN,
    network_evdo BOOLEAN,
    network_cdma BOOLEAN,
    network_lte BOOLEAN,

    sound_loudspeaker BOOLEAN,
    sound_jack35 BOOLEAN,

    display_size FLOAT,
    display_ratio FLOAT,

    display_width INTEGER,
    display_height INTEGER,
    display_density INTEGER,

    display_multitouch BOOLEAN,

    display_type VARCHAR,
    display_protection VARCHAR,

    color_black BOOLEAN,
    color_white BOOLEAN,
    color_blue BOOLEAN,
    color_red BOOLEAN,
    color_pink BOOLEAN,
    color_silver BOOLEAN,
    color_gray BOOLEAN,
    color_yellow BOOLEAN,
    color_green BOOLEAN,
    color_gold BOOLEAN,
    color_orange BOOLEAN,

    sim_type VARCHAR,
    platform VARCHAR
)
