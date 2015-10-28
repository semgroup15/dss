package dss.commands.db;

import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;
import dss.models.price.Price;

public class InitCommand implements Runnable {
    @Override
    public void run() {
        Manufacturer.manager.createTable();
        Device.manager.createTable();
        Price.manager.createTable();
    }
}
