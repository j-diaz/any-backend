package com.scriptfuzz.parser;


import java.util.Map;
import java.util.Set;

/**
 * Created by jdiaz on 10/4/16.
 */
public class ValueParser {

    public static String replace(String query, Map<String, Object> values) throws IllegalArgumentException {

        Set<String> keys = values.keySet();
        if (!keys.isEmpty()) {

            for (String key: keys) {
                // All keys should represent values to replace
                if (!query.contains(key)) throw new IllegalArgumentException("Value map does not match query placeholders");

                String placeholder = "["+key+"]";
                //System.out.print(placeholder + " values.get(key): "+values.get(key) + " ");

                query = query.replace(placeholder, "" +values.get(key));
            }
        }

        return query;
    }
}
