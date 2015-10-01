package dss.commands.interop;

import java.util.List;

import dss.interop.GSMArena;
import dss.interop.DeviceLoader;
import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;

public class ExpandCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("GSMArena");
        System.out.println("Expanding current database with device details");

        DeviceLoader.Cache cache = new DeviceLoader.Cache();

        DeviceLoader.Handler handler = new DeviceLoader.Handler() {
            @Override
            public void onStart(Device device) {
                Manufacturer manufacturer =
                        cache.manufacturer.get(device.manufacturerId);
                System.out.println(String.format(
                        "Requesting %s %s", manufacturer.name, device.name));
            }

            @Override
            public void onFinish(Device device) {
                System.out.println("done.");
            }
        };

        List<Device> devices = Device.manager.select(Device.SELECT_ALL);
        System.out.println(String.format("%d devices", devices.size()));

        DeviceLoader loader =
                new DeviceLoader(cache, new GSMArena(), handler);
        loader.loadAll(devices);
    }
}
