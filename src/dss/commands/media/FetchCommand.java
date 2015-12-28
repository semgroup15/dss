package dss.commands.media;

import dss.models.base.Model;
import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;

import java.io.File;
import java.util.List;

public class FetchCommand implements Runnable {

    public static void main(String[] args) {
        new FetchCommand().run();
    }

    @Override
    public void run() {
        System.out.println("Media");
        System.out.println("Fetching missing device images");

        List<Device> devices = Device.manager.select(Device.SELECT_ALL);
        for (Device device : devices) {
            System.out.println(String.format(
                    "%s %s", device.getManufacturer().name, device.name));

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
