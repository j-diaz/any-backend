package com.scriptfuzz.backend;

import java.util.Map;

/**
 * Created by jdiaz on 8/28/16.
 */
public interface Backend {

    BackendResultSet read(Object query, Map<String, Object> values);

    BackendResultSet insert(Object query, Map<String, Object> values);

    BackendResultSet update(Object query, Map<String, Object> values);

    BackendResultSet delete(Object query, Map<String, Object> values);
}
