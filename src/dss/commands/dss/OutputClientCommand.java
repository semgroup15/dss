package dss.commands.dss;

import java.util.List;

import dss.models.device.Device;

public class OutputClientCommand implements Runnable {

    public static void main(String[] args) {
        new OutputClientCommand().run();
    }

    @Override
    public void run() {
        System.out.println("DSS Output Client");

        List<Device> devices = Device.manager.select(new Device.QueryBuilder()
                .byManufacturerName("samsung")
                .byName("galaxy"));

        System.out.println(devices.size() + " devices found");
    }
}
