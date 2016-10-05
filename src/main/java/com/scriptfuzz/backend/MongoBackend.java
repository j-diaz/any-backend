package com.scriptfuzz.backend;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Mongo db backend
 * */
public class MongoBackend implements Backend{

    private MongoClient mongoClient;
    private DB db;

    public MongoBackend(String host, int port, String dbName){
        mongoClient = new MongoClient(host, port);
        mongoClient.getDatabase(dbName);
    }

    @Override
    public BackendResultSet read(Object query, Map<String, Object> values) {
        String collection = (String) values.get("collection");
        return null;
    }

    @Override
    public BackendResultSet insert(Object query, Map<String, Object> values) {
        String collection = (String) values.get("collection");
        return insertHelper(query, collection);
    }

    @Override
    public BackendResultSet update(Object query, Map<String, Object> values) {
        String collection = (String) values.get("collection");
        return null;
    }

    @Override
    public BackendResultSet delete(Object query, Map<String, Object> values) {
        String collection = (String) values.get("collection");
        return null;
    }

    private BackendResultSet insertHelper(Object query, String collection){
        DBCollection coll = db.getCollection(collection);
        if(query instanceof BasicDBObject){
            coll.insert((BasicDBObject) query);
        }
        return null;
    }

    /**
     * Simple client to show usage
     * @param args
     */
    public static void main(String[] args) {
        Backend backend = new MongoBackend("localhost", 27017, "test");
        BasicDBObject doc = new BasicDBObject();
        doc.put("id", 123);
        doc.put("name", "Jose");

        Map<String, Object> values = new HashMap<>();
        values.put("collection", "tweets");
        backend.insert(doc , values);
    }
}
