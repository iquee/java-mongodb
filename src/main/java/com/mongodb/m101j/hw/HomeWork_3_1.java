package com.mongodb.m101j.hw;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taira on 26/10/16.
 */
public class HomeWork_3_1 {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("school");
        MongoCollection<Document> collection = database.getCollection("students");

        // parametros para a pesquisa
        List<Document> params = new ArrayList<Document>();
        params.add(new Document("$unwind", "$scores"));
        params.add(new Document("$match", new Document("scores.type","homework")));
        params.add(new Document("$sort", new Document("scores.score", 1)));
        params.add(new Document("$sort", new Document("_id", 1)));

        AggregateIterable<Document> aggregate = collection.aggregate(params);
        int index = -1; // indice para quando alterar o ID
        for (Document document: aggregate) {
            if (index != document.getInteger("_id")){
                index = document.getInteger("_id"); // novo id

                Document scores = (Document) document.get("scores"); // scores esta ordenado, do menor para o maior
                double score = scores.getDouble("score"); // pega o menor score

                BasicDBObject update = new BasicDBObject(
                        "scores",
                        new BasicDBObject("score", score)
                                .append("type", "homework")
                );
                collection.updateOne(document, new BasicDBObject("$pull", update));
            }
        }
    }
}
