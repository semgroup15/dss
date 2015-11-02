package dss.models.review;

import dss.models.Model;

import java.sql.SQLException;

public class Review extends Model{

    public long id;
    public long deviceId;
    public String text;
    public int responsiveness;
    public int screen;
    public int battery;

    /*
     * Manager
     */

    public static Manager<Review> manager =
            new Manager<>(Review.class);

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
        responsiveness = result.nextInt();
        screen = result.nextInt();
        battery = result.nextInt();
        text = result.nextString();
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setNextLong(deviceId);
        statement.setNextInt(responsiveness);
        statement.setNextInt(screen);
        statement.setNextInt(battery);
        statement.setNextString(text);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setNextLong(deviceId);
        statement.setNextInt(responsiveness);
        statement.setNextInt(screen);
        statement.setNextInt(battery);
        statement.setNextString(text);
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



