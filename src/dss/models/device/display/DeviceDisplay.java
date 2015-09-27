package dss.models.device.display;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceDisplay extends Model {

    public long deviceId;

    public double size;
    public double ratio;

    public int width;
    public int height;

    public boolean multitouch;

    public long typeId;
    public long protectionId;

    /*
     * Manager
     */

    public static Manager<DeviceDisplay> manager =
            new Manager<>(DeviceDisplay.class);

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

        size = result.getDouble(2);
        ratio = result.getDouble(3);

        width = result.getInt(4);
        height = result.getInt(5);

        multitouch = result.getBoolean(6);

        typeId = result.getLong(7);
        protectionId = result.getLong(8);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, deviceId);

        statement.setDouble(2, size);
        statement.setDouble(3, ratio);

        statement.setInt(4, width);
        statement.setInt(5, height);

        statement.setBoolean(6, multitouch);

        statement.setLong(7, typeId);
        statement.setLong(8, protectionId);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setDouble(1, size);
        statement.setDouble(2, ratio);

        statement.setInt(3, width);
        statement.setInt(4, height);

        statement.setBoolean(5, multitouch);

        statement.setLong(6, typeId);
        statement.setLong(7, protectionId);

        statement.setLong(8, deviceId);
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
