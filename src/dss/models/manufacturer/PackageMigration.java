package dss.models.manufacturer;

import dss.models.Model;

public class PackageMigration extends Model.CompositeMigration {
    public PackageMigration() {
        super(new Model.Migration() {
            @Override
            public void forward() {
                Manufacturer.manager.createTable();
            }

            @Override
            public void backward() {
                Manufacturer.manager.dropTable();
            }
        });
    }
}
