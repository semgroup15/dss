package dss.models.manufacturer;

import java.sql.SQLException;

import dss.models.Model;

public class Manufacturer extends Model {

    public long id;
    public String name;

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
     * Manager
     */

    public static Manager<Manufacturer> manager =
            new Manager<>(Manufacturer.class);

    @Override
    protected Manager<?> getManager() {
        return manager;
    }
}
