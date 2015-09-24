package dss.models.device.com;

import dss.models.Model;

public class PackageMigration extends Model.CompositeMigration {
    public PackageMigration() {
        super(new Model.Migration() {
            @Override
            public void forward() {
                DeviceCom.manager.createTable();
            }

            @Override
            public void backward() {
                DeviceCom.manager.dropTable();
            }
        });
    }
}
