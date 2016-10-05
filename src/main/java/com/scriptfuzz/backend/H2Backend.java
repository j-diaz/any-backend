package com.scriptfuzz.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by jdiaz on 9/21/16.
 */
public class H2Backend extends JDBCPooledBackend implements Backend{

    private static final Logger logger = LoggerFactory.getLogger(PostgresBackend.class);

    public H2Backend(String path) throws IOException {
        super(path);
    }

    public H2Backend() {
        super();
    }

    @Override
    public BackendResultSet read(Object query, Map<String, Object> values) {

        return super.readQueryHelper(query, values);
    }

    @Override
    public BackendResultSet insert(Object query, Map<String, Object> values) {
        return super.insertQueryHelper(query, values);
    }

    @Override
    public BackendResultSet update(Object query, Map<String, Object> values) { return super.updateQueryHelper(query, values);}

    @Override
    public BackendResultSet delete(Object query, Map<String, Object> values) {
        return super.updateQueryHelper(query, values);
    }

    @Override
    public BackendResultSet createOrModify(Object query, Map<String, Object> values) {
        return super.createOrAlterQueryHelper(query, values);
    }

}
