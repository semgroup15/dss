package dss.commands.gsmarena;

import java.util.List;

import dss.interop.GSMArena;
import dss.interop.GSMArena.DeviceResult;
import dss.models.Model;
import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;

public class DeviceCommand implements Runnable {

    @Override
    public void run() {
        System.out.println("GSMArena Device");

        List<Device> devices = Device.manager.select(Device.SELECT_ALL);
        Model.Cache<Manufacturer, Long> cache =
                new Model.Cache<>(new Manufacturer.Loader());

        for (Device device : devices) {
            Manufacturer manufacturer = cache.get(device.manufacturerId);

            System.out.println(String.format(
                    "Requesting %s %s", manufacturer.name, device.name));

            try {
                DeviceResult result = GSMArena.getDevice(device.id);
            } catch (GSMArena.Exception exception) {
                System.out.println("Exception retrieving device information");
            }
        }

        System.out.println(devices.size());
    }
}
