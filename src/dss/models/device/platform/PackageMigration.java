package dss.models.device.platform;

import dss.models.Model;

public class PackageMigration extends Model.CompositeMigration {
    public PackageMigration() {
        super(new Model.Migration() {
            @Override
            public void forward() {
                PlatformChipset.manager.createTable();
                PlatformCPUType.manager.createTable();
                PlatformGPU.manager.createTable();
                PlatformOS.manager.createTable();
                PlatformOSVersion.manager.createTable();
            }

            @Override
            public void backward() {
                PlatformChipset.manager.dropTable();
                PlatformCPUType.manager.dropTable();
                PlatformGPU.manager.dropTable();
                PlatformOS.manager.dropTable();
                PlatformOSVersion.manager.dropTable();
            }
        }, new Model.Migration() {
            @Override
            public void forward() {
                DevicePlatform.manager.createTable();
            }

            @Override
            public void backward() {
                DevicePlatform.manager.dropTable();
            }
        });
    }
}
