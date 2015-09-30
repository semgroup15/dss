package dss.models.device.platform;

import java.sql.SQLException;

import dss.models.Model;

public class PlatformOSVersion extends Model {

    public long id;
    public String name;
    public long osId;

    /*
     * Manager
     */

    public static Manager<PlatformOSVersion> manager =
            new Manager<>(PlatformOSVersion.class);

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

        id = result.getLong(1);
    }

    @Override
    protected void syncResultSet(Manager.RestrictedResult result)
            throws SQLException {

        id = result.getLong(1);
        name = result.getString(2);
        osId = result.getLong(3);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setString(1, name);
        statement.setLong(2, osId);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setString(1, name);
        statement.setLong(2, osId);
        statement.setLong(3, id);
    }

    @Override
    protected void prepareDelete(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, id);
    }

    /*
     * Queries
     */

    public static final String SELECT_ALL = "all";
    public static final String SELECT_OS = "os";
    public static final String SELECT_ID = "id";
    public static final String SELECT_NAME = "name";
}
