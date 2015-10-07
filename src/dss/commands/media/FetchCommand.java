package dss.commands.media;

import java.io.File;
import java.util.List;

import dss.models.Model;
import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;

public class FetchCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("Media");
        System.out.println("Fetching missing device images");

        Model.Cache<Manufacturer, Long> manufacturerCache =
                new Model.Cache<>(new Manufacturer.Loader());

        List<Device> devices = Device.manager.select(Device.SELECT_ALL);
        for (Device device : devices) {
            Manufacturer manufacturer =
                    manufacturerCache.get(device.manufacturerId);
            System.out.println(String.format(
                   "%s %s", manufacturer.name, device.name));

            File imageFile = device.getImageFile();
            if (imageFile == null) {
                System.out.println(String.format(
                        "Fetching image '%s'", device.imageUrl));
                device.fetchImageFile();
            } else {
                System.out.println(String.format(
                        "Image found '%s'", imageFile.getPath()));
            }
        }
    }
}