package dss.models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.sqlite.SQLiteConfig;

import dss.models.Model.DB.Context;

/**
 * Base data model
 */
public abstract class Model {

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
    public static class DB {

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

            // Settings
            private static final String DRIVER = "org.sqlite.JDBC";
            private static final String PATH = "db.sqlite3";
            private static final String CONN = "jdbc:sqlite:" + PATH;
            private static final int TIMEOUT = 30;

            // Current connection
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

                // Enable foreign keys
                SQLiteConfig config = new SQLiteConfig();
                config.enforceForeignKeys(true);

                return DriverManager.getConnection(
                        CONN, config.toProperties());
            }

            /**
             * Configure statement for execution.
             * @param statement Statement
             * @throws SQLException
             */
            private void configure(Statement statement) throws SQLException {
                statement.setQueryTimeout(TIMEOUT);
                statement.closeOnCompletion();
            }

            /**
             * Get a statement for executing queries.
             * @return Statement
             * @throws SQLException
             */
            public Statement statement() throws SQLException {
                Statement statement = connection.createStatement();
                configure(statement);
                return statement;
            }

            /**
             * Get a prepared statement.
             * @param query SQL query
             * @return Prepared statement
             * @throws SQLException
             */
            public PreparedStatement prepared(String query)
                    throws SQLException {

                PreparedStatement statement =
                    connection.prepareStatement(query);
                configure(statement);
                return statement;
            }
        }
    }

    /**
     * {@code Exception} thrown when a model method is not applicable.
     */
    public static class NotApplicable extends RuntimeException {

        private static final long serialVersionUID = 1L;
    }

    /**
     * {@code Exception} thrown when an instance is not found.
     */
    public static class DoesNotExist extends Exception {

        private static final long serialVersionUID = 1L;

        private Class<?> type;
        private String name;
        private Object[] parameters;

        /**
         * Initialize {@code DoesNotExist} exception.
         * @param type Type of model.
         * @param name Query name or description.
         * @param parameters Query parameters.
         */
        public DoesNotExist(Class<?> type, String name, Object[] parameters) {
            super();

            this.type = type;
            this.name = name;
            this.parameters = parameters;
        }

        public String toString() {
            String[] parameters = new String[this.parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                parameters[i] = this.parameters[i].toString();
            }

            return String.format(
                    "%s with parameters %s(%s) does not exist",
                    this.type, this.name, String.join(", ", parameters));
        }
    }

    /**
     * Manager for general operations.
     *
     * <ul>
     *   <li>{@code CREATE TABLE}</li>
     *   <li>{@code DROP TABLE}</li>
     *   <li>Bulk {@code INSERT}</li>
     *   <li>Bulk {@code UPDATE}</li>
     *   <li>Bulk {@code DELETE}</li>
     *   <li>{@code SELECT}</li>
     * </ul>
     *
     * @param <T> Model class
     */
    public static class Manager<T extends Model> {

        /**
         * Wrapper for providing parameters to a {@code PreparedStatement}.
         */
        public static class RestrictedStatement {

            private PreparedStatement statement;

            /**
             * Initialize {@code RestrictedStatement}.
             * @param statement {@code PreparedStatement} to wrap
             */
            public RestrictedStatement(PreparedStatement statement) {
                this.statement = statement;
            }

            public void setLong(int position, long value)
                    throws SQLException {

                statement.setLong(position, value);
            }

            public void setInt(int position, int value) 
                    throws SQLException {

                statement.setInt(position, value);
            }

            public void setString(int position, String value)
                    throws SQLException {

                statement.setString(position, value);
            }
        }

        /**
         * Wrapper for obtaining values from a {@code ResultSet}.
         */
        public static class RestrictedResult {

            private ResultSet result;

            /**
             * Initialize {@code RestrictedResult}.
             * @param result {@code ResultSet} to wrap
             */
            public RestrictedResult(ResultSet result) {
                this.result = result;
            }

            public long getLong(int position) throws SQLException {
                return result.getLong(position);
            }

            public int getInt(int position) throws SQLException {
                return result.getInt(position);
            }

            public String getString(int position) throws SQLException {
                return result.getString(position);
            }
        }

        /**
         * Query generated programmatically.
         */
        public static class QueryProvider {

            private List<Object> parameters = new ArrayList<Object>();
            private String query;

            /**
             * Initialize {@code QueryProvider}.
             */
            public QueryProvider() {}

            /**
             * Append a piece of SQL.
             * @param part SQL to append
             */
            public void add(String part) {
                this.query += part;
            }

            /**
             * Append query parameter.
             * @param object Parameter
             */
            public void add(Object... parameters) {
                this.parameters.addAll(Arrays.asList(parameters));
            }
        }

        /**
         * Loader and cache for SQL files.
         * @param <T> Model class
         */
        public static class QueryLoader<T> {

            private static final String ENCODING = "UTF-8";
            private static final String RESOURCE = "%s.%s.sql";

            private Class<T> type;
            private Map<String, String> cache = new HashMap<String, String>();

            /**
             * Initialize {@code QueryLoader}.
             * @param type Model type
             */
            public QueryLoader(Class<T> type) {
                this.type = type;
            }

            /**
             * Get the specified query file.
             * @param name Query name
             * @return SQL contents
             */
            public String get(String name) {
                String content = cache.get(name);

                if (content == null) {
                    try {
                        content = load(name);
                    } catch (FileNotFoundException exception) {
                        throw new RuntimeException(exception);
                    } catch (IOException exception) {
                        throw new RuntimeException(exception);
                    }

                    cache.put(name, content);
                }

                return content;
            }

            /**
             * Load the specified query file.
             * @param name Query name
             * @return SQL contents
             * @throws FileNotFoundException
             * @throws IOException
             */
            private String load(String name)
                    throws FileNotFoundException, IOException {

                String file = String.format(
                        RESOURCE, type.getSimpleName(), name);

                InputStream stream = type.getResourceAsStream(file);
                if (stream == null) {
                    throw new FileNotFoundException(file);
                }

                String content = IOUtils.toString(stream, ENCODING);
                stream.close();
                return content;
            }
        }

        private Class<T> type;
        private QueryLoader<T> queryLoader;

        /**
         * Initialize {@code Manager} for the specified model type.
         * @param type Model type
         */
        public Manager(Class<T> type) {
            this.type = type;
            this.queryLoader = new QueryLoader<T>(type);
        }

        /**
         * Add meaningful manager information to {@code SQLException}.
         * @param exception Exception to wrap
         * @param name Query name
         * @return Wrapped {@code SQLException}
         */
        protected SQLException wrap(SQLException exception, String name) {
            String message = String.format(
                    "%s '%s' %s", type.getSimpleName(), name,
                    exception.getMessage());
            return new SQLException(message, exception);
        }

        /**
         * Load a SQL {@code ResultSet} into a model list.
         * @param result Query result
         * @param rows Model list
         * @throws SQLException
         */
        public void load(ResultSet result, List<T> rows) throws SQLException {
            while (result.next()) {
                T row;

                try {
                    row = type.newInstance();
                    row.exists = true;
                } catch (InstantiationException exception) {
                    throw new RuntimeException(exception);
                } catch (IllegalAccessException exception) {
                    throw new RuntimeException(exception);
                }

                row.syncResultSet(new RestrictedResult(result));
                rows.add(row);
            }
            result.close();
        }

        /*
         * Table create and drop
         */

        protected static final String QUERY_TABLE_CREATE = "table.create";
        protected static final String QUERY_TABLE_DROP = "table.drop";

        /**
         * Get {@code CREATE TABLE} query.
         * @return SQL
         */
        private String getTableCreateQuery() {
            return queryLoader.get(QUERY_TABLE_CREATE);
        }

        /**
         * Get {@code DROP TABLE} query.
         * @return SQL
         */
        private String getTableDropQuery() {
            return queryLoader.get(QUERY_TABLE_DROP);
        }

        /**
         * Create table.
         */
        public void createTable() {
            DB.execute(new DB.Task<Integer>() {
                @Override
                public Integer execute(DB.Context context)
                        throws SQLException {

                    Statement statement = context.statement();

                    try {
                        return statement.executeUpdate(getTableCreateQuery());
                    } catch (SQLException exception) {
                        throw wrap(exception, QUERY_TABLE_CREATE);
                    }
                }
            });
        }

        /**
         * Drop table.
         */
        public void dropTable() {
            DB.execute(new DB.Task<Integer>() {
                @Override
                public Integer execute(DB.Context context)
                        throws SQLException {

                    Statement statement = context.statement();

                    try {
                        return statement.executeUpdate(getTableDropQuery());
                    } catch (SQLException exception) {
                        throw wrap(exception, QUERY_TABLE_DROP);
                    }
                }
            });
        }

        /*
         * Row insert and update.
         */

        private static final String QUERY_ROW_INSERT = "row.insert";
        private static final String QUERY_ROW_UPDATE = "row.update";
        private static final String QUERY_ROW_DELETE = "row.delete";

        /**
         * Get {@code INSERT} query.
         * @return SQL
         */
        private String getRowInsertQuery() {
            return queryLoader.get(QUERY_ROW_INSERT);
        }

        /**
         * Get {@code UPDATE} query.
         * @return SQL
         */
        private String getRowUpdateQuery() {
            return queryLoader.get(QUERY_ROW_UPDATE);
        }

        /**
         * Get {@code DELETE} query.
         * @return SQL
         */
        private String getRowDeleteQuery() {
            return queryLoader.get(QUERY_ROW_DELETE);
        }

        /**
         * {@code INSERT} multiple instances.
         * @param rows Instances to insert.
         */
        public void insert(List<T> rows) {
            DB.execute(new DB.Task<Integer>() {
                @Override
                public Integer execute(DB.Context context)
                        throws SQLException {

                    PreparedStatement statement =
                            context.prepared(getRowInsertQuery());
                    for (T row : rows) {
                        row.prepareInsert(new RestrictedStatement(statement));
                        statement.addBatch();
                    }

                    try {
                        return statement.executeBatch()[0];
                    } catch (SQLException exception) {
                        throw wrap(exception, QUERY_ROW_INSERT);
                    }
                }
            });
        }

        /**
         * {@code UPDATE} multiple instances.
         * @param rows Instances to update.
         */
        public void update(List<T> rows) {
            DB.execute(new DB.Task<Integer>() {
                @Override
                public Integer execute(DB.Context context)
                        throws SQLException {

                    PreparedStatement statement =
                            context.prepared(getRowUpdateQuery());
                    for (T row : rows) {
                        row.prepareUpdate(new RestrictedStatement(statement));
                        statement.addBatch();
                    }

                    try {
                        return statement.executeBatch()[0];
                    } catch (SQLException exception) {
                        throw wrap(exception, QUERY_ROW_UPDATE);
                    }
                }
            });
        }

        /**
         * {@code DELETE} multiple instances.
         * @param rows Instances to delete.
         */
        public void delete(List<T> rows) {
            DB.execute(new DB.Task<Integer>() {
                @Override
                public Integer execute(DB.Context context)
                        throws SQLException {

                    PreparedStatement statement =
                        context.prepared(getRowDeleteQuery());
                    for (T row : rows) {
                        row.prepareDelete(new RestrictedStatement(statement));
                        statement.addBatch();
                    }

                    try {
                        return statement.executeBatch()[0];
                    } catch (SQLException exception) {
                        throw wrap(exception, QUERY_ROW_DELETE);
                    }
                }
            });
        }

        /*
         * {@code SELECT} query execution
         */

        /**
         * Get full name for {@code SELECT} query.
         * @param name Select query name
         * @return Full name
         */
        private static String getSelectQueryName(String name) {
            return String.format("select.%s", name);
        }

        /**
         * Get {@code SELECT} query.
         * @param name Select query name
         * @return SQL
         */
        private String getSelectQuery(String name) {
            return queryLoader.get(getSelectQueryName(name));
        }

        /**
         * {@code SELECT} instances.
         * @param name Select query name
         * @param parameters Query parameters
         * @return Query result
         */
        public List<T> select(final String name, Object... parameters) {
            return DB.execute(new DB.Task<List<T>>() {
                @Override
                public List<T> execute(DB.Context context)
                        throws SQLException {

                    PreparedStatement statement =
                            context.prepared(getSelectQuery(name));
                    for (int i = 0; i < parameters.length; i++) {
                        statement.setObject(i + 1, parameters[i]);
                    }

                    List<T> rows = new ArrayList<>();
                    try {
                        ResultSet result = statement.executeQuery();
                        load(result, rows);
                    } catch (SQLException exception) {
                        throw wrap(exception, getSelectQueryName(name));
                    }

                    return rows;
                }
            });
        }

        /**
         * {@code SELECT} a single instance.
         * @param name Select query name
         * @param parameters Query parameters
         * @return Query result
         */
        public T get(final String name, Object... parameters)
                throws DoesNotExist {

            List<T> rows = select(name, parameters);
            if (rows.isEmpty()) {
                throw new DoesNotExist(this.type, name, parameters);
            }
            return rows.get(0);
        }

        private static final String QUERY_PROGRAMMATIC = "programmatic";

        /**
         * {@code SELECT} instances with a query generated at runtime.
         * @param provider {@code QueryProvider}
         * @return Query result.
         */
        public List<T> select(final QueryProvider provider) {
            return DB.execute(new DB.Task<List<T>>() {
                @Override
                public List<T> execute(Context context)
                        throws SQLException {

                    PreparedStatement statement =
                            context.prepared(provider.query);
                    for (int i = 0; i < provider.parameters.size(); i++) {
                        statement.setObject(i + 1, provider.parameters.get(i));
                    }

                    List<T> rows = new ArrayList<>();
                    try {
                        ResultSet result = statement.executeQuery();
                        load(result, rows);
                    } catch (SQLException exception) {
                        throw wrap(exception, QUERY_PROGRAMMATIC);
                    }

                    return rows;
                }
            });
        }
    }

    protected boolean exists = false;

    /**
     * Update or insert this model instance.
     */
    public void save() {
        if (exists) {
            update();
        } else {
            insert();
            exists = true;
        }
    }

    /**
     * Get default manager for this model.
     * @return Default manager.
     */
    protected abstract Manager<?> getManager();

    /*
     * Sync with {@code Manager.RestrictedResult}
     */

    /**
     * Update the model with a generated key.
     * @param result Generated key
     * @throws SQLException
     */
    protected abstract void syncGeneratedKey(
            Manager.RestrictedResult result) throws SQLException;

    /**
     * Update the model with row data.
     * @param result Query result
     */
    protected abstract void syncResultSet(
            Manager.RestrictedResult result) throws SQLException;

    /*
     * Prepare with {@code Manager.RestrictedStatement}
     */

    /**
     * Prepare for {@code INSERT} usually providing parameters.
     * @param statement Statement to prepare.
     * @throws SQLException
     */
    protected abstract void prepareInsert(
            Manager.RestrictedStatement statement) throws SQLException;

    /**
     * Prepare for {@code UPDATE} usually providing parameters.
     * @param statement Statement to prepare.
     * @throws SQLException
     */
    protected abstract void prepareUpdate(
            Manager.RestrictedStatement statement) throws SQLException;

    /**
     * Prepare for {@code DELETE} usually providing parameters.
     * @param statement Statement to prepare.
     * @throws SQLException
     */
    protected abstract void prepareDelete(
            Manager.RestrictedStatement statement) throws SQLException;

    /*
     * Query execution
     */

    /**
     * {@code INSERT} this model instance.
     */
    private void insert() {
        DB.execute(new DB.Task<Integer>() {
            @Override
            public Integer execute(DB.Context context) throws SQLException {
                Manager<?> manager = getManager();

                PreparedStatement statement =
                        context.prepared(manager.getRowInsertQuery());
                prepareInsert(new Manager.RestrictedStatement(statement));

                try {
                    int result = statement.executeUpdate();
                    if (result > 0) {
                        ResultSet key = statement.getGeneratedKeys();
                        if (key.next()) {
                            try {
                                syncGeneratedKey(
                                        new Manager.RestrictedResult(key));
                            } catch (NotApplicable exception) {
                                exception.printStackTrace();
                            }
                        }
                        key.close();
                    }
                    return result;
                } catch (SQLException exception) {
                    throw manager.wrap(exception, Manager.QUERY_ROW_INSERT);
                }
            }
        });
    }

    /**
     * {@code UPDATE} this model instance.
     */
    private void update() {
        DB.execute(new DB.Task<Integer>() {
            @Override
            public Integer execute(DB.Context context) throws SQLException {
                Manager<?> manager = getManager();

                PreparedStatement statement =
                        context.prepared(getManager().getRowUpdateQuery());
                prepareUpdate(new Manager.RestrictedStatement(statement));

                try {
                    return statement.executeUpdate();
                } catch (SQLException exception) {
                    throw manager.wrap(exception, Manager.QUERY_ROW_UPDATE);
                }
            }
        });
    }

    /**
     * {@code DELETE} this model instance.
     */
    public void delete() {
        DB.execute(new DB.Task<Integer>() {
            @Override
            public Integer execute(DB.Context context) throws SQLException {
                Manager<?> manager = getManager();

                PreparedStatement statement =
                        context.prepared(getManager().getRowDeleteQuery());
                prepareDelete(new Manager.RestrictedStatement(statement));

                try {
                    return statement.executeUpdate();
                } catch (SQLException exception) {
                    throw manager.wrap(exception, Manager.QUERY_ROW_DELETE);
                }
            }
        });
    }
}