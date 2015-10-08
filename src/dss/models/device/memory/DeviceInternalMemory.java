package dss.models.device.memory;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceInternalMemory extends Model {

    public long deviceId;
    public long internalMemoryId;

    public InternalMemory getInternalMemory() throws Model.DoesNotExist {
        return InternalMemory.manager.get(
                InternalMemory.SELECT_ID, internalMemoryId);
    }

    /*
     * Manager
     */

    public static Manager<DeviceInternalMemory> manager =
            new Manager<>(DeviceInternalMemory.class);

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
        internalMemoryId = result.getLong(2);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, deviceId);
        statement.setLong(2, internalMemoryId);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        throw new Model.NotApplicable();
    }

    @Override
    protected void prepareDelete(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, deviceId);
        statement.setLong(2, internalMemoryId);
    }
}
