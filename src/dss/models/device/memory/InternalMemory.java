package dss.models.device.memory;

import java.sql.SQLException;

import dss.models.Model;

public class InternalMemory extends Model {

    public long id;
    public String name;
    public int size;

    /*
     * Manager
     */

    public static Manager<InternalMemory> manager =
            new Manager<>(InternalMemory.class);

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
        size = result.getInt(3);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setString(1, name);
        statement.setInt(2, size);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setString(1, name);
        statement.setInt(2, size);
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
    public static final String SELECT_ID = "id";
    public static final String SELECT_NAME = "name";

    public static final String SELECT_DEVICE = "device";
}
