package dss.models.device.body;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceBody extends Model {

    public long deviceId;

    public double width;
    public double height;
    public double depth;

    public double weight;

    public long simTypeId;

    public SIMType getSIMType() throws Model.DoesNotExist {
        return SIMType.manager.get(SIMType.SELECT_ID, simTypeId);
    }

    /*
     * Manager
     */

    public static Manager<DeviceBody> manager =
            new Manager<>(DeviceBody.class);

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

        width = result.getDouble(2);
        height = result.getDouble(3);
        depth = result.getDouble(4);

        weight = result.getDouble(5);

        simTypeId = result.getLong(6);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, deviceId);

        statement.setDouble(2, width);
        statement.setDouble(3, height);
        statement.setDouble(4, depth);

        statement.setDouble(5, weight);

        statement.setLong(6, simTypeId);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setDouble(1, width);
        statement.setDouble(2, height);
        statement.setDouble(3, depth);

        statement.setDouble(4, weight);

        statement.setLong(5, simTypeId);

        statement.setLong(6, deviceId);
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
