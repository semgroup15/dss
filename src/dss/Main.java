package dss;

import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        System.out.println("Main");

        DB.execute(new DB.Task<Void>() {
            @Override
            public Void execute(DB.Context context) throws SQLException {
                Statement statement = context.statement();
                statement.executeUpdate("CREATE TABLE user (id integer)");
                return null;
            }
        });
    }
}
