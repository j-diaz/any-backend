package com.scriptfuzz.backend;

import com.scriptfuzz.parser.ValueParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by jdiaz on 8/28/16.
 */
public class BackendUtil {

    private static final Logger logger = LoggerFactory.getLogger(BackendUtil.class);

    public static BackendResultSet convertToBackendResultSet(Object rs, Map<String, Object> values){
        BackendResultSet drs = null;

        if(rs instanceof ResultSet){
            drs = fromSQLResultSet((ResultSet) rs);
        }

        // TODO: Add other wrap function for other backends

        return drs;
    }

    /**
     * Covnerts a SQL ResultSet to BackendResultSet
     * @param rs
     * @return
     */
    private static BackendResultSet fromSQLResultSet(ResultSet rs){
        BackendResultSet drs = new BackendResultSet();
        try {
            ResultSetMetaData meta = rs.getMetaData();
            List<BackendResult> rows = new LinkedList<>();

            final int columnCount = meta.getColumnCount();
            while(rs.next()){
                BackendResult row = new BackendResult();

            //    logger.info("backendmetadata: "+columnCount);
                for(int col = 1; col <= columnCount; col++){
                    Object column = rs.getObject(col);
                   // logger.info("column: "+column);
                    String label = meta.getColumnLabel(col);
                   // logger.info("label: "+label);
                    row.put(label, column);
                }
                rows.add(row);
            } //TODO handle metadata
            drs.setRows(rows);
        } catch (SQLException e) {
            logger.error(String.format("Error getting metadata: %s",e.toString()));
        }
        return drs;
    }

    /**
     * Implement a parser for replacing values
     * @param query
     * @param values
     * @return
     */
    public static String buildSQLQuery(Object query, Map<String, Object> values){
        // Tokens should be replaced
        String queryToRun = (values != null && !values.isEmpty()) ?
                createSQLQuery( (String) query , values) : (String) query;

        return queryToRun;

    }

    private static String createSQLQuery(String q, Map<String, Object> values){
        String newQuery = "";
        if (values == null) {
            newQuery = ValueParser.replace(q, new HashMap<String, Object>());
        } else {
            newQuery = ValueParser.replace(q, values);
        }
        return newQuery;
    }

}
