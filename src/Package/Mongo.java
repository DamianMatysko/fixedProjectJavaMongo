package Package;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import java.util.concurrent.CopyOnWriteArrayList;


public class Mongo {
    public static MongoClient mongoClient;
    public static MongoDatabase mongoDatabase;
    public static MongoCollection<Document> mongoCollection;

    public Mongo() {
        this.mongoClient = new MongoClient("localhost", 27017);
        this.mongoDatabase = mongoClient.getDatabase("company");
        this.mongoCollection = mongoDatabase.getCollection("users");
    }

    public void insertOne() throws Exception {
        Document data = new Document("name", "Peto").append("age", 12).append("status", "enchanted");
        mongoCollection.insertOne(data);
        mongoClient.close();
        System.out.println("success");
    }

    public void insertMany() throws Exception {
        Document data0 = new Document("name", "Adam").append("age", 102).append("status", "out");
        Document data1 = new Document("name", "Samo").append("age", 1).append("status", "ultimate");
        Document data2 = new Document("name", "Duri").append("age", 78).append("status", "traktorista");
        Document data3 = new Document("name", "Jozko").append("age", 34).append("status", "single");

        java.util.List<Document> toInsert = new CopyOnWriteArrayList<>();
        toInsert.add(data0);
        toInsert.add(data1);
        toInsert.add(data2);
        toInsert.add(data3);

        mongoCollection.insertMany(toInsert);
        System.out.println("success");
    }

    public void print() throws Exception {
        try (MongoCursor<Document> mongoCursor = mongoCollection.find().iterator()) {
            while (mongoCursor.hasNext()) {
                System.out.println(mongoCursor.next().toJson());
            }
        }
    }

    public void udate() throws Exception {
        mongoCollection.updateOne(Filters.eq("name", "Peto"), Updates.set("name", "Petko"));
        System.out.println("success");
    }

    public void delete() throws Exception {
        mongoCollection.deleteOne(Filters.eq("name", "Petko"));
        System.out.println("success");
    }

    public void find(String name) throws Exception {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("name", name);

        MongoCursor<Document> mongoCursor = mongoCollection.find().iterator();
        while (mongoCursor.hasNext()) {
            Document doc = mongoCursor.next();
            JSONObject object = new JSONObject(doc.toJson());
            if (object.getString("name").equals(name)) {
                System.out.println(object);
                return;
            }
        }
    }
}