package dss.models.device.camera;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceCameraPrimaryVideo extends Model {

    public long deviceId;
    public long videoId;

    /*
     * Manager
     */

    public static Manager<DeviceCameraPrimaryVideo> manager =
            new Manager<>(DeviceCameraPrimaryVideo.class);

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
