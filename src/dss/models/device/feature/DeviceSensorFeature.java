package dss.models.device.feature;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceSensorFeature extends Model {

    public long deviceId;
    public long sensorFeatureId;

    public SensorFeature getSensorFeature() throws Model.DoesNotExist {
        return SensorFeature.manager.get(
                SensorFeature.SELECT_ID, sensorFeatureId);
    }

    /*
     * Manager
     */

    public static Manager<DeviceSensorFeature> manager =
            new Manager<>(DeviceSensorFeature.class);

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
        sensorFeatureId = result.getLong(2);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, deviceId);
        statement.setLong(2, sensorFeatureId);
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
        statement.setLong(2, sensorFeatureId);
    }
}
