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
