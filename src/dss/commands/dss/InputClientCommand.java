package dss.commands.dss;

import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;

public class InputClientCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("DSS Input Client");

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.name = "Samsung";
        manufacturer.save();

        Device device = new Device();
        device.name = "Galaxy S III";
        device.year = 2013;
        device.manufacturerId = manufacturer.id;
        device.save();
    }
}
