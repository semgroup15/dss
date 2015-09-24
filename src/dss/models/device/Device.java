package dss.models.device;

import java.sql.SQLException;

import dss.models.Model;
import dss.models.manufacturer.Manufacturer;

public class Device extends Model {

    public long id;

    public String name;
    public long year;

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