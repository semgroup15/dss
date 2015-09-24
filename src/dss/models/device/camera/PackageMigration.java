package dss.models.device.camera;

import dss.models.Model;

public class PackageMigration extends Model.CompositeMigration {
    public PackageMigration() {
        super(new Model.Migration() {
            @Override
            public void forward() {
                CameraFeature.manager.createTable();
                CameraVideo.manager.createTable();
            }

            @Override
            public void backward() {
                CameraFeature.manager.dropTable();
                CameraVideo.manager.dropTable();
            }
        }, new Model.Migration() {
            @Override
            public void forward() {
                DeviceCamera.manager.createTable();
                DeviceCameraPrimaryFeature.manager.createTable();
                DeviceCameraPrimaryVideo.manager.createTable();
            }

            @Override
            public void backward() {
                DeviceCamera.manager.dropTable();
                DeviceCameraPrimaryFeature.manager.dropTable();
                DeviceCameraPrimaryVideo.manager.dropTable();
            }
        });
    }
}
