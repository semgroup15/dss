package dss.commands.gsmarena;

import dss.interop.gsmarena.API;
import dss.interop.gsmarena.API.Exception;
import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;

public class QuickSearchCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("GSMArena Quick Search");

        System.out.println("Requesting device list");

        API.QuickSearchResult result;
        try {
            result = API.getQuickSearch();
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }

        System.out.println(String.format(
                "%d manufacturers", result.manufacturers.size()));
        System.out.println(String.format(
                "%d devices", result.devices.size()));

        System.out.println("Saving data");

        Manufacturer.manager.insert(result.manufacturers);
        Device.manager.insert(result.devices);

        System.out.println("Done");
    }
}
