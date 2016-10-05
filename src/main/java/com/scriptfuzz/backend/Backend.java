package com.scriptfuzz.backend;

import java.util.Map;

/**
 * Created by jdiaz on 8/28/16.
 */
public interface Backend {

    /**
     * Perform a read operation on the backend
     * @param query - The inquiry to perform. e.g "SELECT 1", Document object
     * @param values - Values to replace in SQL query. Maybe null for non-relation backends
     * @return An instance of BackendResultSet
     */
    BackendResultSet read(Object query, Map<String, Object> values);


    /**
     * Perform an insert operation on the backend
     * @param query - The inquiry to perform. e.g "INSERT INTO ...", Document object
     * @param values - Values to replace in SQL query. Maybe null for non-relation backends
     * @return An instance of BackendResultSet
     */
    BackendResultSet insert(Object query, Map<String, Object> values);


    /**
     * Perform an update operation on the backend
     * @param query - The inquiry to perform. e.g "UPDATE X SET .. WHERE ...", Document object
     * @param values - Values to replace in SQL query. Maybe null for non-relation backends
     * @return An instance of BackendResultSet
     */
    BackendResultSet update(Object query, Map<String, Object> values);


    /**
     * Perform a delete operation on the backend
     * @param query - The inquiry to perform. e.g "DELETE FROM ... ", Document object
     * @param values - Values to replace in SQL query. Maybe null for non-relation backends
     * @return An instance of BackendResultSet
     */
    BackendResultSet delete(Object query, Map<String, Object> values);


    /**
     * Perform a read operation on the backend
     * @param query - The inquiry to perform. e.g "CREATE TABLE ...", Document object
     * @param values - Values to replace in SQL query. Maybe null for non-relation backends
     * @return An instance of BackendResultSet
     */
    BackendResultSet createOrModify(Object query, Map<String, Object> values);

}
