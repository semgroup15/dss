package dss.models.device.platform;

import java.sql.SQLException;

import dss.models.Model;

public class DevicePlatform extends Model {

    public static class CPU {

        public long platformCPUTypeId;

        public double freq;
        public String raw;
    }

    public long deviceId;

    public long platformOSId;
    public long platformOSCurrentVersionId;
    public long platformOSUpagradeVersionId;

    public long platformChipsetId;
    public long platformGPUId;

    public CPU cpu;

    /*
     * Manager
     */

    public static Manager<DevicePlatform> manager =
            new Manager<>(DevicePlatform.class);

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
