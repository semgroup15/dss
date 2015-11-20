package dss.commands.rand;

import dss.models.device.Device;
import dss.models.review.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ReviewsCommand implements Runnable {

    private static final int MAX = 5;

    public static void main(String[] args) {
        new ReviewsCommand().run();
    }

    @Override
    public void run() {
        System.out.println("Randomizer");
        System.out.println("Randomizing reviews");

        Random random = ThreadLocalRandom.current();

        List<Device> devices = Device.manager.select(Device.SELECT_ALL);
        List<Review> reviews = new ArrayList<>();

        for (Device device : devices) {
            System.out.println(String.format(
                    "%s %s", device.getManufacturer().name, device.name));

            Review review = new Review();
            review.deviceId = device.id;
            review.responsiveness = random.nextInt(MAX + 1);
            review.screen = random.nextInt(MAX + 1);
            review.battery = random.nextInt(MAX + 1);
            reviews.add(review);
        }

        System.out.println("Saving data");
        Review.manager.insert(reviews);
        System.out.println("Done");
    }
}
