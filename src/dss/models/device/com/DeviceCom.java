package dss.models.device.com;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceCom extends Model {

    public int deviceId;

    public boolean wlan;
    public boolean bluetooth;
    public boolean gps;
    public boolean radio;
    public boolean usb;

    /*
     * Manager
     */

    public static Manager<DeviceCom> manager = new Manager<>(DeviceCom.class);

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
