package dss.models.device.camera;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceCameraPrimaryFeature extends Model {

    public long deviceId;
    public long featureId;

    public CameraFeature getCameraFeature() throws Model.DoesNotExist {
        return CameraFeature.manager.get(CameraFeature.SELECT_ID, featureId);
    }

    /*
     * Manager
     */

    public static Manager<DeviceCameraPrimaryFeature> manager =
            new Manager<>(DeviceCameraPrimaryFeature.class);

    @Override
    protected Manager<?> getManager() {
        return manager;
    }

    /*
     * Sync
     */

    @Override
    protected void syncGeneratedKey(Manager.RestrictedResult result)
            throws SQLException {

        throw new Model.NotApplicable();
    }

    @Override
    protected void syncResultSet(Manager.RestrictedResult result)
            throws SQLException {

        deviceId = result.getLong(1);
        featureId = result.getLong(2);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, deviceId);
        statement.setLong(2, featureId);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        throw new Model.NotApplicable();
    }

    @Override
    protected void prepareDelete(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, deviceId);
        statement.setLong(2, featureId);
    }
}
