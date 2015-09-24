CREATE TABLE device_com (
    device_id INTEGER PRIMARY KEY REFERENCES device(id),
    wlan BOOLEAN,
    bluetooth BOOLEAN,
    gps BOOLEAN,
    radio BOOLEAN,
    usb BOOLEAN
)
