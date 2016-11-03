package com.mongodb.m101j.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

/**
 * Created by taira on 03/11/16.
 */
public class MongoDBServer {

    public static final MongoDatabase getDB(){
        String host = "mongodb://mongom101j:mongom101j@ds143737.mlab.com:43737/taira";
        MongoClient client = new MongoClient(new MongoClientURI(host));
        MongoDatabase database = client.getDatabase("taira");
        return database;
    }
}
