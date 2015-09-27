package dss.models.device.camera;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceCamera extends Model {

    public long deviceId;

    public static class Primary {

        public int mp;
        public int width;
        public int height;
    }

    public Primary primary;

    public static class Secondary {

        public boolean has;
        public int mp;
        public long videoId;
    }

    public Secondary secondary;

    public DeviceCamera() {
        primary = new Primary();
        secondary = new Secondary();
    }

    /*
     * Manager
     */

    public static Manager<DeviceCamera> manager =
            new Manager<>(DeviceCamera.class);

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

        deviceId = result.getLong(1);
    }

    @Override
    protected void syncResultSet(Manager.RestrictedResult result)
            throws SQLException {

        deviceId = result.getLong(1);

        primary.mp = result.getInt(2);
        primary.width = result.getInt(3);
        primary.height = result.getInt(4);

        secondary.has = result.getBoolean(5);
        secondary.mp = result.getInt(6);
        secondary.videoId = result.getLong(7);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, deviceId);

        statement.setInt(2, primary.mp);
        statement.setInt(3, primary.width);
        statement.setInt(4, primary.height);

        statement.setBoolean(5, secondary.has);
        statement.setInt(6, secondary.mp);
        statement.setLong(7, secondary.videoId);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setInt(1, primary.mp);
        statement.setInt(2, primary.width);
        statement.setInt(3, primary.height);

        statement.setBoolean(4, secondary.has);
        statement.setInt(5, secondary.mp);
        statement.setLong(6, secondary.videoId);

        statement.setLong(7, deviceId);
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
