package dss.models.manufacturer;

import java.sql.SQLException;

import dss.models.Model;

public class Manufacturer extends Model {

    public long id;
    public String name;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (getClass() != object.getClass()) {
            return false;
        }

        Manufacturer manufacturer = (Manufacturer) object;
        return manufacturer.id == id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
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

    public static class Loader implements Cache.Loader<Manufacturer, Long> {
        @Override
        public Manufacturer load(Long key) throws DoesNotExist {
            return manager.get(SELECT_ID, key);
        }

        @Override
        public Manufacturer create(Long key) {
            return new Manufacturer();
        }
    }

    public static Model.Cache<Manufacturer, Long> cache =
            new Model.Cache<>(new Loader());

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

        id = result.nextLong();
        name = result.nextString();
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setNextLong(id);
        statement.setNextString(name);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setNextString(name);
        statement.setNextLong(id);
    }

    @Override
    protected void prepareDelete(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setNextLong(id);
    }

    /*
     * Queries
     */

    public static final String SELECT_ALL = "all";
    public static final String SELECT_ID = "id";
    public static final String SELECT_NAME = "name";
    public static final String SELECT_COMMON = "common";
}

