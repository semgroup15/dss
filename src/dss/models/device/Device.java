package dss.models.device;

import java.sql.SQLException;

import dss.models.Model;
import dss.models.manufacturer.Manufacturer;

public class Device extends Model {

    public long id;

    public String name;
    public int year;

    public long manufacturerId;

    public Manufacturer getManufacturer() throws Model.DoesNotExist {
        return Manufacturer.manager.get(Manufacturer.SELECT_ID, manufacturerId);
    }

    /*
     * Manager
     */

    public static Manager<Device> manager = new Manager<>(Device.class);

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
        year = result.getInt(3);

        manufacturerId = result.getLong(4);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setString(1, name);
        statement.setInt(2, year);

        statement.setLong(3, manufacturerId);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setString(1, name);
        statement.setInt(2, year);

        statement.setLong(3, manufacturerId);

        statement.setLong(4, id);
    }

    @Override
    protected void prepareDelete(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, id);
    }

    /*
     * Queries
     */

    public static final String SELECT_ID = "id";
}
