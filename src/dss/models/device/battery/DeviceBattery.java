package dss.models.device.battery;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceBattery extends Model {

    public long deviceId;

    public String descr;

    public int sleep;
    public int talk;
    public int music;
    public int rating;

    /*
     * Manager
     */

    public static Manager<DeviceBattery> manager =
            new Manager<>(DeviceBattery.class);

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