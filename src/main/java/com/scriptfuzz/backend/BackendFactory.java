package com.scriptfuzz.backend;


import java.io.IOException;

/**
 * Factory of multiple backends
 */
public class BackendFactory {

    /**
     * Factory for different JDBCPool managed database servers.
     * @param db
     * @return
     */
    public static Backend getBackend(String db, String propPath) throws IOException {
        Backend backend = null;
        if("pg".equalsIgnoreCase(db) || "postgres".equalsIgnoreCase(db)){
            backend = new PostgresBackend(propPath);
        } else if("h2".equalsIgnoreCase(db)){
            backend = new H2Backend(propPath);
        } else if("mongo".equalsIgnoreCase(db)){
            // TODO: Implement mongodb backend
        }else if("casssandra".equalsIgnoreCase(db)){
            // TODO: Implement cassandra backend
        }
        // TODO: Add other possible backend implementations
        return backend;
    }
}
