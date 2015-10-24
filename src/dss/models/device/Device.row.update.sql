UPDATE device
SET
    name = ?,
    image_url = ?,
    year = ?,

    manufacturer_id = ?,

    battery_sleep = ?,
    battery_talk = ?,
    battery_music = ?,

    body_width = ?,
    body_height = ?,
    body_depth = ?,
    body_weight = ?,

    camera_primary_mp = ?,
    camera_primary_width = ?,
    camera_primary_height = ?,

    camera_secondary_has = ?,
    camera_secondary_mp = ?,

    com_wlan = ?,
    com_bluetooth = ?,
    com_gps = ?,
    com_radio = ?,
    com_usb = ?,
    com_nfc = ?,

    sensor_accelerometer = ?,
    sensor_barometer = ?,
    sensor_compass = ?,
    sensor_gyro = ?,
    sensor_magnetometer = ?,
    sensor_proximity = ?,

    memory_ram_size = ?,
    memory_internal_size = ?,

    memory_sd = ?,
    memory_micro_sd = ?,
    memory_micro_sdhc = ?,
    memory_mmc = ?,
    memory_mmc_mobile = ?,

    network_gsm  = ?,
    network_umts  = ?,
    network_hspa  = ?,
    network_evdo  = ?,
    network_cdma  = ?,
    network_lte  = ?,

    sound_loudspeaker = ?,
    sound_jack35 = ?,

    display_size = ?,
    display_ratio = ?,

    display_width = ?,
    display_height = ?,
    display_density = ?,

    display_multitouch = ?,

    display_type = ?,
    display_protection = ?,

    color_black = ?,
    color_white = ?,
    color_blue = ?,
    color_red = ?,
    color_pink = ?,
    color_silver = ?,
    color_gray = ?,
    color_yellow = ?,
    color_green = ?,
    color_gold = ?,
    color_orange = ?,

    sim_type = ?,
    platform = ?

WHERE id = ?
