package com.scriptfuzz.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Generic abstraction over the equivalent of a sql Result object.
 */
public class BackendResult extends HashMap{

    private static final Logger logger = LoggerFactory.getLogger(BackendResult.class);


    public BackendResult(){
        super();
    }

    public void put(String key, Object value){
        super.put(key,value);
    }

    public String getString(String key){
        return (String) super.get(key);
    }

    public int getInt(String key){
        return Integer.parseInt( "" + super.get(key) );
    }

    public long getLong(String key){

        return Long.parseLong( "" + super.get(key) );
    }

    public List<Object> getList(String key){
        return new ArrayList<>( (List) super.get(key) );
    }

}
