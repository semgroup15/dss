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

        throw new Model.NotApplicable();
    }

    @Override
    protected void syncResultSet(Manager.RestrictedResult result)
            throws SQLException {

        deviceId = result.getLong(1);

        descr = result.getString(2);

        sleep = result.getInt(3);
        talk = result.getInt(4);
        music = result.getInt(5);
        rating = result.getInt(6);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, deviceId);

        statement.setString(2, descr);

        statement.setInt(3, sleep);
        statement.setInt(4, talk);
        statement.setInt(5, music);
        statement.setInt(6, rating);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setString(1, descr);

        statement.setInt(2, sleep);
        statement.setInt(3, talk);
        statement.setInt(4, music);
        statement.setInt(5, rating);

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
