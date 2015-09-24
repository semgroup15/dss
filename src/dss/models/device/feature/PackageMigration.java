package dss.models.device.feature;

import dss.models.Model;

public class PackageMigration extends Model.CompositeMigration {
    public PackageMigration() {
        super(new Model.Migration() {
            @Override
            public void forward() {
                MessagingFeature.manager.createTable();
                SensorFeature.manager.createTable();
            }

            @Override
            public void backward() {
                MessagingFeature.manager.dropTable();
                SensorFeature.manager.dropTable();
            }
        }, new Model.Migration() {
            @Override
            public void forward() {
                DeviceMessagingFeature.manager.createTable();
                DeviceSensorFeature.manager.createTable();
            }

            @Override
            public void backward() {
                DeviceMessagingFeature.manager.dropTable();
                DeviceSensorFeature.manager.dropTable();
            }
        });
    }
}
