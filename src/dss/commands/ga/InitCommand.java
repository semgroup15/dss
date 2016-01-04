package dss.commands.ga;

import dss.Developer;
import dss.ga.GSMArena;
import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;

@Developer({
    Developer.Value.SEBASTIAN,
})
public class InitCommand implements Runnable {

    public static void main(String[] args) {
        new InitCommand().run();
    }

    @Override
    public void run() {
        System.out.println("GSMArena");
        System.out.println("Initializing with minimal device listing");

        GSMArena.QuickSearchResult result;
        try {
            result = new GSMArena().getQuickSearch();
        } catch (GSMArena.Exception exception) {
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
