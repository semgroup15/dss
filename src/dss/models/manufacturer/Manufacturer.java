package dss.models.manufacturer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import dss.models.Model;

public class Manufacturer extends Model {

    public long id;
    public String name;

    @Override
    protected void prepareInsert(PreparedStatement statement)
            throws SQLException {

        statement.setString(1, name);
    }

    @Override
    protected void prepareUpdate(PreparedStatement statement)
            throws SQLException {

        statement.setString(1, name);
        statement.setLong(2, id);
    }

    @Override
    protected void prepareDelete(PreparedStatement statement)
            throws SQLException {

        statement.setLong(1, id);
    }

    /*
     * Manager
     */

    public static Manager<Manufacturer> manager =
            new Manager<Manufacturer>(Manufacturer.class);

    @Override
    protected Manager<?> getManager() {
        return manager;
    }
}
