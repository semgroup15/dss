package dss.models.device.memory;

import java.sql.SQLException;

import dss.models.Model;

public class MemorySlot extends Model {

    public long id;
    public String name;

    /*
     * Manager
     */

    public static Manager<MemorySlot> manager =
            new Manager<>(MemorySlot.class);

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
