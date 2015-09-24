package dss.models.device.display;

import dss.models.Model;

public class PackageMigration extends Model.CompositeMigration {
    public PackageMigration() {
        super(new Model.Migration() {
            @Override
            public void forward() {
                DisplayProtection.manager.createTable();
                DisplayType.manager.createTable();
            }

            @Override
            public void backward() {
                DisplayProtection.manager.dropTable();
                DisplayType.manager.createTable();
            }
        }, new Model.Migration() {
            @Override
            public void forward() {
                DeviceDisplay.manager.createTable();
            }

            @Override
            public void backward() {
                DeviceDisplay.manager.dropTable();
            }
        });
    }
}
