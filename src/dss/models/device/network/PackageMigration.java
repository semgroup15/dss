package dss.models.device.network;

import dss.models.Model;

public class PackageMigration extends Model.CompositeMigration {
    public PackageMigration() {
        super(new Model.Migration() {
            @Override
            public void forward() {
                NetworkTechnology.manager.createTable();
            }

            @Override
            public void backward() {
                NetworkTechnology.manager.dropTable();
            }
        }, new Model.Migration() {
            @Override
            public void forward() {
                DeviceNetworkTechnology.manager.createTable();
            }

            @Override
            public void backward() {
                DeviceNetworkTechnology.manager.dropTable();
            }
        });
    }
}
