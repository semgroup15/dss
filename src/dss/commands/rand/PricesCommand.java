package dss.commands.rand;

import dss.models.device.Device;
import dss.models.price.Price;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PricesCommand implements Runnable {

    private static final int MAX = 1000;

    public static void main(String[] args) {
        new PricesCommand().run();
    }

    @Override
    public void run() {
        System.out.println("Randomizer");
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
            price.cost = random.nextInt(MAX + 1);
            prices.add(price);
        }

        System.out.println("Saving data");
        Price.manager.insert(prices);
        System.out.println("Done");
    }
}
