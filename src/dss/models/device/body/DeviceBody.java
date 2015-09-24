package dss.models.device.body;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceBody extends Model {

    public long deviceId;

    public double width;
    public double height;
    public double depth;

    public double weight;

    public long simTypeId;

    /*
     * Manager
     */

    public static Manager<DeviceBody> manager =
            new Manager<>(DeviceBody.class);

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
