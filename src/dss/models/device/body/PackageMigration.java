package dss.models.device.body;

import dss.models.Model;

public class PackageMigration extends Model.CompositeMigration {
    public PackageMigration() {
        super(new Model.Migration() {
            @Override
            public void forward() {
                SIMType.manager.createTable();
            }

            @Override
            public void backward() {
                SIMType.manager.dropTable();
            }
        }, new Model.Migration() {
            @Override
            public void forward() {
                DeviceBody.manager.createTable();
            }

            @Override
            public void backward() {
                DeviceBody.manager.dropTable();
            }
        });
    }
}
