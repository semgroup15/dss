package dss.models.price;

import dss.models.Model;

import java.sql.SQLException;

public class Price extends Model {

    public long id;
    public long deviceId;
    public String retailer;
    public double cost;

    /*
     * Manager
     */

    public static Manager<Price> manager =
            new Manager<>(Price.class);

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

        id = result.nextLong();
    }

    @Override
    protected void syncResultSet(Manager.RestrictedResult result)
            throws SQLException {

        id = result.nextLong();
        deviceId = result.nextLong();
        retailer = result.nextString();
        cost = result.nextDouble();
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setNextDouble(cost);
        statement.setNextString(retailer);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setNextDouble(cost);
        statement.setNextString(retailer);
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

    public static final String SELECT_DEVICE = "device";
}
