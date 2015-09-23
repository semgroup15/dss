package dss.models.manufacturer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dss.models.Model;

public class Manufacturer extends Model {

    public long id;
    public String name;

    @Override
    protected void syncGeneratedKey(ResultSet result) throws SQLException {
        id = result.getLong(1);
    }

    @Override
    protected void syncResultSet(ResultSet result) throws SQLException {
        id = result.getLong(1);
        name = result.getString(2);
    }

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
            new Manager<>(Manufacturer.class);

    @Override
    protected Manager<?> getManager() {
        return manager;
    }
}
