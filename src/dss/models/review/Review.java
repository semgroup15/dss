package dss.models.review;

import dss.models.base.Model;

import java.sql.SQLException;

public class Review extends Model {

    public long id;
    public long deviceId;
    public String text;
    public int responsiveness;
    public int screen;
    public int battery;

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (getClass() != object.getClass()) {
            return false;
        }

        Review review = (Review) object;
        return review.id == id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

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
     * Observer
     */

    public static Observer<Review> observer = new Observer<>();

    @Override
    protected void insert() {
        super.insert();
        observer.trigger(Observer.Event.INSERT, this);
    }

    @Override
    protected void update() {
        super.update();
        observer.trigger(Observer.Event.UPDATE, this);
    }

    @Override
    public void delete() {
        super.delete();
        observer.trigger(Observer.Event.DELETE, this);
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



