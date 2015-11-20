package dss.commands.retail;

import dss.models.device.Device;
import dss.models.price.Price;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomizeCommand implements Runnable {

    public static void main(String[] args) {
        new RandomizeCommand().run();
    }

    @Override
    public void run() {
        System.out.println("Retail");
        System.out.println("Randomizing prices");

        Random random = ThreadLocalRandom.current();

        List<Device> devices = Device.manager.select(Device.SELECT_ALL);
        List<Price> prices = new ArrayList<>();

        for (Device device : devices) {
            System.out.println(String.format(
                    "%s %s", device.getManufacturer().name, device.name));

            Price price = new Price();
            price.deviceId = device.id;
            price.retailer = Price.Retailer.RANDOM;
            price.cost = random.nextInt(1000);
            prices.add(price);
        }

        System.out.println();

        System.out.println("Saving data");
        Price.manager.insert(prices);
        System.out.println("Done");
    }
}
