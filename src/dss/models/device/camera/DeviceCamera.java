package dss.models.device.camera;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceCamera extends Model {

    public static class Primary {

        public int mp;

        public int width;
        public int height;
    }

    public static class Secondary {

        public boolean has;
        public int mp;

        public long videoId;
    }

    public long deviceId;

    public Primary primary;
    public Secondary secondary;

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
