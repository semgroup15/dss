package dss.models.device.sound;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceSound extends Model {

    public long deviceId;

    public boolean loudspeaker;
    public boolean jack35;

    /*
     * Manager
     */

    public static Manager<DeviceSound> manager =
            new Manager<>(DeviceSound.class);

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

        loudspeaker = result.getBoolean(2);
        jack35 = result.getBoolean(3);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, deviceId);

        statement.setBoolean(2, loudspeaker);
        statement.setBoolean(3, jack35);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setBoolean(1, loudspeaker);
        statement.setBoolean(2, jack35);

        statement.setLong(3, deviceId);
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
