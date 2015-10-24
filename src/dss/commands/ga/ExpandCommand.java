package dss.commands.ga;

import java.util.List;

import dss.ga.GSMArena;
import dss.models.device.Device;

public class ExpandCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("GSMArena");
        System.out.println("Expanding current database with device details");

        GSMArena gsmArena = new GSMArena();

        List<Device> devices = Device.manager.select(Device.SELECT_ALL);
        for (Device device : devices) {
            try {
                System.out.println(String.format(
                        "Requesting %s %s",
                        device.getManufacturer().name, device.name));

                gsmArena.updateDevice(device);
                device.save();

            } catch (GSMArena.Exception exception) {
            }
        }
    }
}
