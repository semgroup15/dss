package dss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database
 *
 * <ul>
 *   <li>Keeps all database settings in one place</li>
 *   <li>Establishes the {@code Connection} to the database</li>
 *   <li>Encapsulates what developers can do in a {@code Context}</li>
 *   <li>Throws database exceptions at runtime</li>
 * </ul>
 */
public class DB {

    /**
     * Task that performs database operations.
     * @param <T> Return type
     */
    public static interface Task<T> {
        /**
         * Perform database operations.
         * @param context Task execution context.
         * @return Value provided to {@code DB.execute(...)}
         * @throws SQLException Exception to be thrown at runtime.
         */
        public T execute(Context context) throws SQLException;
    }

    /**
     * Execute the specified task.
     * @param task Task to execute.
     * @return Result provided by task.
     */
    public static <T> T execute(Task<T> task) {
        try {
            Context context = new Context();

            context.start();
            T result = task.execute(context);
            context.finish();

            return result;

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Task execution context.
     */
    public static class Context {

        private static final String DRIVER = "org.sqlite.JDBC";
        private static final String PATH = "db.sqlite3";
        private static final int TIMEOUT = 30;

        private Connection connection;

        /**
         * Initialize context for performing operations.
         * @throws SQLException
         */
        private void start() throws SQLException {
            this.connection = connect();
        }

        /**
         * Clean up context after performing operations.
         * @throws SQLException
         */
        private void finish() throws SQLException {
            this.connection.close();
        }

        /**
         * Connect to the default database.
         * @return Database connection.
         * @throws SQLException
         */
        private static Connection connect() throws SQLException {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException exception) {
                throw new RuntimeException(exception);
            }

            return DriverManager.getConnection(
                    String.format("jdbc:sqlite:%s", PATH));
        }

        /**
         * Get a statement for executing queries.
         * @return Statement
         * @throws SQLException
         */
        public Statement statement() throws SQLException {
            Statement statement = this.connection.createStatement();
            statement.setQueryTimeout(TIMEOUT);
            return statement;
        }
    }
}