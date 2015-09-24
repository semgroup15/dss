package dss.models.device.feature;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceMessagingFeature extends Model {

    public long deviceId;
    public long messagingFeatureId;

    /*
     * Manager
     */

    public static Manager<DeviceMessagingFeature> manager =
            new Manager<>(DeviceMessagingFeature.class);

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
    }

    @Override
    protected void syncResultSet(Manager.RestrictedResult result)
            throws SQLException {
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {
    }

    @Override
    protected void prepareDelete(Manager.RestrictedStatement statement)
            throws SQLException {
    }

    /*
     * Queries
     */

    public static final String SELECT_ID = "id";
}
