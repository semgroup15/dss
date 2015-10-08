package dss.models.device.misc;

import java.sql.SQLException;

import dss.models.Model;

public class DeviceColor extends Model {

    public long deviceId;
    public long colorId;

    public Color getColor() throws Model.DoesNotExist {
        return Color.manager.get(Color.SELECT_ID, colorId);
    }

    /*
     * Manager
     */

    public static Manager<DeviceColor> manager =
            new Manager<>(DeviceColor.class);

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
        colorId = result.getLong(2);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, deviceId);
        statement.setLong(2, colorId);
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
        statement.setLong(2, colorId);
    }
}
