package com.scriptfuzz.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a PosgreSQL database backend
 * Because of time constraint will defer proper implementation of this
 * layer for later
 */
public class PostgresBackend extends JDBCPooledBackend implements Backend{

    private static final Logger logger = LoggerFactory.getLogger(PostgresBackend.class);

    /**
     * Should take direct url e.g.  jdbc:postgresql://user/password@localhohost/dbname
     * @param pathToProp The path to the property file to initialize jdbc connection pool
     */
    public PostgresBackend(String pathToProp) throws IOException {
        super(pathToProp);
    }

    /**
     *
     * @param query
     * @param values
     * @return
     */
    @Override
    public BackendResultSet read(Object query, Map<String, Object> values){
        return super.readQueryHelper(query, values);
    }

    /**
     *
     * @param query
     * @param values
     * @return
     */
    @Override
    public BackendResultSet insert(Object query, Map<String, Object> values) {
        return super.insertQueryHelper(query, values);
    }

    /**
     *
     * @param query
     * @param values
     * @return
     */
    @Override
    public BackendResultSet update(Object query, Map<String, Object> values) {
        return super.updateQueryHelper(query, values);
    }

    /**
     *
     * @param query
     * @param values
     * @return
     */
    @Override
    public BackendResultSet delete(Object query, Map<String, Object> values) {
        return insertQueryHelper(query, values);
    }

}