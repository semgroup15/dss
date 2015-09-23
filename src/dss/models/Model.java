package dss.models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import dss.DB;
import dss.DB.Context;

/**
 * Base data model
 */
public abstract class Model {

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

            public String getString(int position) throws SQLException {
                return result.getString(position);
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

        /*
         * Table create and drop
         */

        /**
         * Get {@code CREATE TABLE} query.
         * @return SQL
         */
        private String getTableCreateQuery() {
            return queryLoader.get("table.create");
        }

        /**
         * Get {@code DROP TABLE} query.
         * @return SQL
         */
        private String getTableDropQuery() {
            return queryLoader.get("table.drop");
        }

        /**
         * Create table.
         */
        public void createTable() {
            DB.execute(new DB.Task<Integer>() {
                @Override
                public Integer execute(DB.Context context) throws SQLException {
                    Statement statement = context.statement();
                    return statement.executeUpdate(getTableCreateQuery());
                }
            });
        }

        /**
         * Drop table.
         */
        public void dropTable() {
            DB.execute(new DB.Task<Integer>() {
                @Override
                public Integer execute(DB.Context context) throws SQLException {
                    Statement statement = context.statement();
                    return statement.executeUpdate(getTableDropQuery());
                }
            });
        }

        /*
         * Row insert and update.
         */

        /**
         * Get {@code INSERT} query.
         * @return SQL
         */
        private String getRowInsertQuery() {
            return queryLoader.get("row.insert");
        }

        /**
         * Get {@code UPDATE} query.
         * @return SQL
         */
        private String getRowUpdateQuery() {
            return queryLoader.get("row.update");
        }

        /**
         * Get {@code DELETE} query.
         * @return SQL
         */
        private String getRowDeleteQuery() {
            return queryLoader.get("row.delete");
        }

        /**
         * {@code INSERT} multiple instances.
         * @param rows Instances to insert.
         */
        public void insert(List<T> rows) {
            DB.execute(new DB.Task<Integer>() {
                @Override
                public Integer execute(DB.Context context) throws SQLException {
                    PreparedStatement statement =
                            context.prepared(getRowInsertQuery());
                    for (T row : rows) {
                        row.prepareInsert(new RestrictedStatement(statement));
                        statement.addBatch();
                    }
                    return statement.executeBatch()[0];
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
                public Integer execute(DB.Context context) throws SQLException {
                    PreparedStatement statement =
                            context.prepared(getRowUpdateQuery());
                    for (T row : rows) {
                        row.prepareUpdate(new RestrictedStatement(statement));
                        statement.addBatch();
                    }
                    return statement.executeBatch()[0];
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
                public Integer execute(DB.Context context) throws SQLException {
                    PreparedStatement statement =
                        context.prepared(getRowDeleteQuery());
                    for (T row : rows) {
                        row.prepareDelete(new RestrictedStatement(statement));
                        statement.addBatch();
                    }
                    return statement.executeBatch()[0];
                }
            });
        }

        /*
         * Custom query
         */

        /**
         * Get {@code SELECT} query.
         * @return SQL
         */
        private String getSelectQuery(String name) {
            return queryLoader.get("select." + name);
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
                public List<T> execute(Context context) throws SQLException {
                    // Prepare statement
                    PreparedStatement statement =
                            context.prepared(getSelectQuery(name));
                    for (int i = 0; i < parameters.length; i++) {
                        statement.setObject(i + 1, parameters[i]);
                    }

                    // Populate objects with row data
                    List<T> rows = new ArrayList<T>();
                    ResultSet result = statement.executeQuery();
                    while (result.next()) {
                        T row;
                        try {
                            row = type.newInstance();
                        } catch (InstantiationException exception) {
                            throw new RuntimeException(exception);
                        } catch (IllegalAccessException exception) {
                            throw new RuntimeException(exception);
                        }
                        row.syncResultSet(new RestrictedResult(result));
                        rows.add(row);
                    }
                    result.close();

                    return rows;
                }
            });
        }
    }

    /**
     * Get default manager for this model.
     * @return Default manager.
     */
    protected abstract Manager<?> getManager();

    private boolean exists = false;

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
     * Update the model with a generated key.
     * @param result Generated key
     * @throws SQLException
     */
    protected abstract void syncGeneratedKey(Manager.RestrictedResult result)
            throws SQLException;

    /**
     * Update the model with row data.
     * @param result Query result
     */
    protected abstract void syncResultSet(Manager.RestrictedResult result)
            throws SQLException;

    /**
     * Prepare for {@code INSERT} usually providing parameters.
     * @param statement Statement to prepare.
     * @throws SQLException
     */
    protected abstract void prepareInsert(Manager.RestrictedStatement statement)
            throws SQLException;

    /**
     * Prepare for {@code UPDATE} usually providing parameters.
     * @param statement Statement to prepare.
     * @throws SQLException
     */
    protected abstract void prepareUpdate(Manager.RestrictedStatement statement)
            throws SQLException;

    /**
     * Prepare for {@code DELETE} usually providing parameters.
     * @param statement Statement to prepare.
     * @throws SQLException
     */
    protected abstract void prepareDelete(Manager.RestrictedStatement statement)
            throws SQLException;

    /**
     * {@code INSERT} this model instance.
     */
    private void insert() {
        DB.execute(new DB.Task<Integer>() {
            @Override
            public Integer execute(DB.Context context) throws SQLException {
                PreparedStatement statement =
                        context.prepared(getManager().getRowInsertQuery());
                prepareInsert(new Manager.RestrictedStatement(statement));

                int result = statement.executeUpdate();
                if (result > 0) {
                    // Update model with generated key
                    ResultSet key = statement.getGeneratedKeys();
                    if (key.next()) {
                        syncGeneratedKey(new Manager.RestrictedResult(key));
                    }
                    key.close();
                }
                return result;
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
                PreparedStatement statement =
                        context.prepared(getManager().getRowUpdateQuery());
                prepareUpdate(new Manager.RestrictedStatement(statement));
                return statement.executeUpdate();
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
                PreparedStatement statement =
                        context.prepared(getManager().getRowDeleteQuery());
                prepareDelete(new Manager.RestrictedStatement(statement));
                return statement.executeUpdate();
            }
        });
    }
}
