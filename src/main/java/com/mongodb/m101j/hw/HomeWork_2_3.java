/*
 * Copyright 2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.m101j.hw;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.m101j.util.MongoDBServer;
import org.bson.Document;

public class HomeWork_2_3 {
    public static void main(String[] args) {
        MongoCollection<Document> collection = MongoDBServer.getDB().getCollection("grades");

        // collection.drop();
        FindIterable<Document> sort = collection.find(
                new Document("type", "homework"))
                .sort(new Document("student_id", 1)
                        .append("score", 1));

        Integer idToRemove = Integer.MIN_VALUE;
        for (Document doc : sort) {
            int id = doc.getInteger("student_id");
            if (id != idToRemove){
                collection.deleteOne(doc);
                idToRemove = doc.getInteger("student_id");
                System.out.println("removendo: " + doc);
            } else{
                System.out.println("sobrando: " + doc);
            }
        }
    }
}
