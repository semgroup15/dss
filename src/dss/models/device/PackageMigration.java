package dss.models.device;

import dss.models.Model;

public class PackageMigration extends Model.CompositeMigration {
    public PackageMigration() {
        super(new Model.Migration() {
            @Override
            public void forward() {
                Device.manager.createTable();
            }

            @Override
            public void backward() {
                Device.manager.dropTable();
            }
        }, new Model.CompositeMigration(
            new dss.models.device.battery.PackageMigration(),
            new dss.models.device.body.PackageMigration(),
            new dss.models.device.camera.PackageMigration(),
            new dss.models.device.com.PackageMigration(),
            new dss.models.device.display.PackageMigration(),
            new dss.models.device.feature.PackageMigration(),
            new dss.models.device.memory.PackageMigration(),
            new dss.models.device.misc.PackageMigration(),
            new dss.models.device.network.PackageMigration(),
            new dss.models.device.platform.PackageMigration(),
            new dss.models.device.sound.PackageMigration()
        ));
    }
}
