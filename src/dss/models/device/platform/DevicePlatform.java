package dss.models.device.platform;

import java.sql.SQLException;

import dss.models.Model;

public class DevicePlatform extends Model {

    public long deviceId;

    public long platformOSId;
    public long platformOSCurrentVersionId;
    public long platformOSUpgradeVersionId;

    public long platformChipsetId;
    public long platformGPUId;

    public PlatformOS getPlatformOS() throws Model.DoesNotExist {
        return PlatformOS.manager.get(PlatformOS.SELECT_ID, platformOSId);
    }

    public PlatformOSVersion getPlatformOSCurrentVersion()
            throws Model.DoesNotExist {

        return PlatformOSVersion.manager.get(
                PlatformOSVersion.SELECT_ID, platformOSCurrentVersionId);
    }

    public PlatformOSVersion getPlatformOSUpgradeVersion()
            throws Model.DoesNotExist {

        return PlatformOSVersion.manager.get(
                PlatformOSVersion.SELECT_ID, platformOSUpgradeVersionId);
    }

    public PlatformChipset getPlatformChipset() throws Model.DoesNotExist {
        return PlatformChipset.manager.get(
                PlatformChipset.SELECT_ID, platformChipsetId);
    }

    public PlatformGPU getPlatformGPU() throws Model.DoesNotExist {
        return PlatformGPU.manager.get(PlatformGPU.SELECT_ID, platformGPUId);
    }

    public static class CPU {

        public long platformCPUTypeId;
        public double freq;
        public String raw;

        public PlatformCPUType getPlatformCPUType() throws Model.DoesNotExist {
            return PlatformCPUType.manager.get(
                    PlatformCPUType.SELECT_ID, platformCPUTypeId);
        }
    }

    public CPU cpu;

    public DevicePlatform() {
        cpu = new CPU();
    }

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

        throw new Model.NotApplicable();
    }

    @Override
    protected void syncResultSet(Manager.RestrictedResult result)
            throws SQLException {

        deviceId = result.getLong(1);

        platformOSId = result.getLong(2);
        platformOSCurrentVersionId = result.getLong(3);
        platformOSUpgradeVersionId = result.getLong(4);

        platformChipsetId = result.getLong(5);
        platformGPUId = result.getLong(6);

        cpu.platformCPUTypeId = result.getLong(7);
        cpu.freq = result.getDouble(8);
        cpu.raw = result.getString(9);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, deviceId);

        statement.setLong(2, platformOSId);
        statement.setLong(3, platformOSCurrentVersionId);
        statement.setLong(4, platformOSUpgradeVersionId);

        statement.setLong(5, platformChipsetId);
        statement.setLong(6, platformGPUId);

        statement.setLong(7, cpu.platformCPUTypeId);
        statement.setDouble(8, cpu.freq);
        statement.setString(9, cpu.raw);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, platformOSId);
        statement.setLong(2, platformOSCurrentVersionId);
        statement.setLong(3, platformOSUpgradeVersionId);

        statement.setLong(4, platformChipsetId);
        statement.setLong(5, platformGPUId);

        statement.setLong(6, cpu.platformCPUTypeId);
        statement.setDouble(7, cpu.freq);
        statement.setString(8, cpu.raw);

        statement.setLong(9, deviceId);
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
