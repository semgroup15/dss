package dss.models.user;

import dss.Developer;
import dss.models.base.Model;

import java.sql.SQLException;

@Developer({
    Developer.Value.TRIXIE,
    Developer.Value.AHMED,
    Developer.Value.JIM,
})
public class User extends Model {

    public long id;
    public String username;
    public String password;

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (getClass() != object.getClass()) {
            return false;
        }

        User user = (User) object;
        return user.id == id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    /*
     * Manager
     */

    public static Manager<User> manager =
            new Manager<>(User.class);

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
        username = result.nextString();
        password = result.nextString();
    }

    /*
     * Prepare
     */

    @Override
    protected void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setNextString(username);
        statement.setNextString(password);
    }

    @Override
    protected void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException {

        statement.setNextString(username);
        statement.setNextString(password);
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

    public static final String SELECT_AUTH = "auth";
}
