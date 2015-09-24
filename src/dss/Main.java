package dss;

import dss.models.device.Device;
import dss.models.device.battery.DeviceBattery;
import dss.models.manufacturer.Manufacturer;

public class Main {

    public static void main(String[] args) {
        Manufacturer.manager.createTable();
        Device.manager.createTable();
        DeviceBattery.manager.createTable();
    }
}
