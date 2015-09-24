package dss.models.device.misc;

import dss.models.Model;

public class PackageMigration extends Model.CompositeMigration {
    public PackageMigration() {
        super(new Model.Migration() {
            @Override
            public void forward() {
                Color.manager.createTable();
            }

            @Override
            public void backward() {
                Color.manager.dropTable();
            }
        }, new Model.Migration() {
            @Override
            public void forward() {
                DeviceColor.manager.createTable();
            }

            @Override
            public void backward() {
                DeviceColor.manager.dropTable();
            }
        });
    }
}
