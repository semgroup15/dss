package dss.models;

import dss.models.Model;

public class PackageMigration extends Model.CompositeMigration {
    public PackageMigration() {
        super(
            new dss.models.manufacturer.PackageMigration(),
            new dss.models.device.PackageMigration()
        );
    }
}
