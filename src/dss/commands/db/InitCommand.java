package dss.commands.db;

import dss.Developer;
import dss.models.device.Device;
import dss.models.manufacturer.Manufacturer;
import dss.models.price.Price;
import dss.models.review.Review;
import dss.models.user.User;

@Developer({
    Developer.Value.SEBASTIAN,
})
public class InitCommand implements Runnable {

    public static void main(String[] args) {
        new InitCommand().run();
    }

    @Override
    public void run() {
        Manufacturer.manager.createTable();
        Device.manager.createTable();
        Price.manager.createTable();
        Review.manager.createTable();
        User.manager.createTable();
    }
}
