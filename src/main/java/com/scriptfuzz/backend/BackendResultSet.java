package com.scriptfuzz.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * Generic abstraction over a ResultSet object.
 */
public class BackendResultSet {

    private static final Logger logger = LoggerFactory.getLogger(BackendResultSet.class);

    private List<BackendResult> rows;
    private BackendResultSetMetadata meta;
    private long insertedId = -1;

    /**
     * Create a BackendResultSet based off a SQL ResultSet
     * @param rs
     * @param values
     */
    public BackendResultSet(ResultSet rs, Map<String, Object> values){
        BackendResultSet that = BackendUtil.convertToBackendResultSet(rs, values);
        rows = that.getRows();
        meta = that.getMetadata();
        long thatId = that.getInsertedId();
        if(thatId > -1){
            insertedId = thatId;
        }
    }

    public BackendResultSet(){}

    public List<BackendResult> getRows() {
        return rows;
    }

    public void setRows(List<BackendResult> rows) {
        this.rows = rows;
    }

    public int getCount() {
        return rows.size();
    }

    public BackendResultSetMetadata getMetadata(){
        return meta;
    }

    public long getInsertedId(){
        return insertedId;
    }

    public void setInsertedId(long id){
        insertedId = id;
    }


}
