package dss.models.manufacturer;

import java.sql.SQLException;

import dss.models.Model;

public class Manufacturer extends Model {

    public long id;
    public String name;

    /*
     * Manager
     */

    public static Manager<Manufacturer> manager =
            new Manager<>(Manufacturer.class);

    @Override
    protected Manager<?> getManager() {
        return manager;
    }

    public static Cache.Loader<Manufacturer, Long> loader =
            new Cache.Loader<Manufacturer, Long>() {
                @Override
                public Manufacturer load(Long key) throws Model.DoesNotExist {
                    return manager.get(SELECT_ID, key);
                }

                @Override
                public Manufacturer create() {
                    return new Manufacturer();
                }
            };

    public static Cache<Manufacturer, Long> cache = new Cache<>(loader);

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

        id = result.getLong(1);
        name = result.getString(2);
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setLong(1, id);
        statement.setString(2, name);
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
     * Queries
     */

    public static final String SELECT_ALL = "all";
    public static final String SELECT_ID = "id";
    public static final String SELECT_NAME = "name";
}
