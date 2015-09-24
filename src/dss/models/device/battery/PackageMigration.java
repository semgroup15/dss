package dss.models.device.battery;

import dss.models.Model;

public class PackageMigration extends Model.CompositeMigration {
    public PackageMigration() {
        super(new Model.Migration() {
            @Override
            public void forward() {
                DeviceBattery.manager.createTable();
            }

            @Override
            public void backward() {
                DeviceBattery.manager.dropTable();
            }
        });
    }
}
