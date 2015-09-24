package dss.models.device.sound;

import dss.models.Model;

public class PackageMigration extends Model.CompositeMigration {
    public PackageMigration() {
        super(new Model.Migration() {
            @Override
            public void forward() {
                DeviceSound.manager.createTable();
            }

            @Override
            public void backward() {
                DeviceSound.manager.dropTable();
            }
        });
    }
}
