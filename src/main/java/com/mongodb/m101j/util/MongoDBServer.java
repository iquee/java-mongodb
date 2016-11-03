package com.mongodb.m101j.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

/**
 * Created by taira on 03/11/16.
 */
public class MongoDBServer {

    public static String host = "mongodb://mongom101j:mongom101j@ds143737.mlab.com:43737/taira";

    public static final MongoDatabase getDB(boolean remote, String db){
        if (!remote){
            host = "mongodb://localhost:27017";
        }
        MongoClient client = new MongoClient(new MongoClientURI(MongoDBServer.host));
        MongoDatabase database = client.getDatabase(db);
        return database;
    }
}
