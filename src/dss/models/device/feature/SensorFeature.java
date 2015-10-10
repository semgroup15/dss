package dss.models.device.feature;

import java.sql.SQLException;

import dss.models.Model;

public class SensorFeature extends Model {

    public long id;
    public String name;

    /*
     * Manager
     */

    public static Manager<SensorFeature> manager =
            new Manager<>(SensorFeature.class);

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
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setString(1, name);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setString(1, name);
        statement.setLong(2, id);
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
    public static final String SELECT_ID = "id";
    public static final String SELECT_NAME = "name";

    public static final String SELECT_DEVICE = "device";
}
