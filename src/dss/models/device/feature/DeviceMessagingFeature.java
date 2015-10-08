package dss.models.device.feature;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceMessagingFeature extends Model {

    public long deviceId;
    public long messagingFeatureId;

    public MessagingFeature getMessagingFeature() throws Model.DoesNotExist {
        return MessagingFeature.manager.get(
                MessagingFeature.SELECT_ID, messagingFeatureId);
    }

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

        throw new Model.NotApplicable();
    }

    @Override
    protected void syncResultSet(Manager.RestrictedResult result)
            throws SQLException {

        deviceId = result.getLong(1);
        messagingFeatureId = result.getLong(2);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, deviceId);
        statement.setLong(2, messagingFeatureId);
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
        statement.setLong(2, messagingFeatureId);
    }
}
