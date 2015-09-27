package dss.models.device.com;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceCom extends Model {

    public long deviceId;

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

        throw new Model.NotApplicable();
    }

    @Override
    protected void syncResultSet(Manager.RestrictedResult result)
            throws SQLException {

        deviceId = result.getLong(1);

        wlan = result.getBoolean(2);
        bluetooth = result.getBoolean(3);
        gps = result.getBoolean(4);
        radio = result.getBoolean(5);
        usb = result.getBoolean(6);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, deviceId);

        statement.setBoolean(2, wlan);
        statement.setBoolean(3, bluetooth);
        statement.setBoolean(4, gps);
        statement.setBoolean(5, radio);
        statement.setBoolean(6, usb);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setBoolean(1, wlan);
        statement.setBoolean(2, bluetooth);
        statement.setBoolean(3, gps);
        statement.setBoolean(4, radio);
        statement.setBoolean(5, usb);

        statement.setLong(6, deviceId);
    }

    @Override
    protected void prepareDelete(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, deviceId);
    }

    /*
     * Queries
     */

    public static final String SELECT_ID = "id";
}
