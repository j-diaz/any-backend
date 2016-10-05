package com.scriptfuzz.backend;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Created by jdiaz on 8/28/16.
 */
public abstract class JDBCPooledBackend {
    private static final Logger logger = LoggerFactory.getLogger(JDBCPooledBackend.class);

    protected HikariDataSource ds;

    public JDBCPooledBackend(String path) throws IOException {
        InputStream in = JDBCPooledBackend.class.getClassLoader().getResourceAsStream(path);
        Properties properties = new Properties();
        properties.load(in);
        HikariConfig config = new HikariConfig(properties);
        config.setAutoCommit(true);
        config.setMaximumPoolSize(20);
        config.setIdleTimeout(15000);
        config.setConnectionTimeout(15000);
        config.setMaxLifetime(18000);
        ds = new HikariDataSource(config);
    }


    public JDBCPooledBackend(Properties properties) {
        HikariConfig config = new HikariConfig(properties);
        config.setAutoCommit(true);
        config.setMaximumPoolSize(20);
        config.setIdleTimeout(15000);
        config.setConnectionTimeout(15000);
        config.setMaxLifetime(18000);
        ds = new HikariDataSource(config);
    }

        public JDBCPooledBackend() throws IOException {

        String dataSourceClassName = System.getProperty("dataSourceClassName");
        String dataSourceUser = System.getProperty("dataSourceUser");
        String dataSourcePassword = System.getProperty("dataSourcePassword");
        String dataSourceDatabaseName = System.getProperty("dataSourceDatabaseName");
        String dataSourcePortNumber = System.getProperty("dataSourcePortNumber");
        String dataSourceServerName = System.getProperty("dataSourceServerName");

        boolean autoCommit = Boolean.parseBoolean( System.getProperty("autoCommit") );
        int maxPoolSize = Integer.parseInt( System.getProperty("maximumPoolSize") );
        int idleTimeout = Integer.parseInt( System.getProperty("idleTimeout") );
        int connectionTimeout = Integer.parseInt( System.getProperty("connectionTimeout") );
        int maxLifetime = Integer.parseInt( System.getProperty("maxLifetime") );

        HikariConfig config = new HikariConfig();
        config.setAutoCommit(autoCommit);
        config.setMaximumPoolSize(maxPoolSize);
        config.setIdleTimeout(idleTimeout);
        config.setConnectionTimeout(connectionTimeout);
        config.setMaxLifetime(maxLifetime);

        ds = new HikariDataSource(config);
    }

    protected Connection getPoolConnection(){
        // Attempt to get connection
        Connection conn = null;
        try {
            conn = ds.getConnection(); // Makes it clear
        } catch (SQLException e) {
            logger.error("Error getting connection: "+e.toString());
        }
        return conn;
    }    // Attempt to get connection

    protected BackendResultSet readQueryHelper(Object query, Map<String, Object> values){
        String queryToRun = BackendUtil.buildSQLQuery(query , values);

        Connection conn = getPoolConnection();

        BackendResultSet drs = new BackendResultSet();
        if(conn != null){
            Statement stmt = null;
            try{
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(queryToRun);
                drs = new BackendResultSet(rs, new HashMap<String, Object>());

            } catch (SQLException e) {
                logger.error(e.getMessage());
            } finally {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
            }
        }
        return drs;
    }

    protected BackendResultSet insertQueryHelper(Object query, Map<String, Object> values) {
        // Build actual query to run
        String queryToRun = BackendUtil.buildSQLQuery(query, values);

        Connection conn = getPoolConnection();

        // If was able to get connection attempt to run query.
        BackendResultSet drs = new BackendResultSet();
        if (conn != null) {
            Statement stmt = null;
            try {
                // Run query and get the result
                stmt = conn.createStatement();
                long id = stmt.executeUpdate(queryToRun);
                drs.setInsertedId(id);
                logger.info(String.format("Running query: %s, id: %s", queryToRun, id));
            } catch (SQLException e) {
                logger.error(e.toString());
            } finally {
                // Don't forget to close connection
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
            }
        }
        // Return it even if empty. Wrap should handle this case.
        return drs;
    }

    protected BackendResultSet updateQueryHelper(Object query, Map<String, Object> values) {
        // Build actual query to run
        String queryToRun = BackendUtil.buildSQLQuery(query, values);

        Connection conn = getPoolConnection();

        // If was able to get connection attempt to run query.
        BackendResultSet drs = new BackendResultSet();
        if (conn != null) {
            Statement stmt = null;
            try {
                // Run query and get the result
                stmt = conn.prepareStatement(queryToRun);
                long id = stmt.executeUpdate(queryToRun);

                logger.info(String.format("Running query: %s, id: %s", queryToRun, id));
            } catch (SQLException e) {
                logger.error(e.toString());
            } finally {
                // Don't forget to close connection
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
            }
        }
        // Return it even if empty. Wrap should handle this case.
        return drs;
    }
}
