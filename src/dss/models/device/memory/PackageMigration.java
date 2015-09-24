package dss.models.device.memory;

import dss.models.Model;

public class PackageMigration extends Model.CompositeMigration {
    public PackageMigration() {
        super(new Model.Migration() {
            @Override
            public void forward() {
                InternalMemory.manager.createTable();
                MemorySlot.manager.createTable();
            }

            @Override
            public void backward() {
                InternalMemory.manager.dropTable();
                MemorySlot.manager.dropTable();
            }
        }, new Model.Migration() {
            @Override
            public void forward() {
                DeviceInternalMemory.manager.createTable();
                DeviceMemorySlot.manager.createTable();
                DeviceRAM.manager.createTable();
            }

            @Override
            public void backward() {
                DeviceInternalMemory.manager.dropTable();
                DeviceMemorySlot.manager.dropTable();
                DeviceRAM.manager.dropTable();
            }
        });
    }
}
