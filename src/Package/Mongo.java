package Package;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import java.util.concurrent.CopyOnWriteArrayList;


public class Mongo {
    public static MongoClient mongoClient = new MongoClient("localhost", 27017);
    public static MongoDatabase mongoDatabase = mongoClient.getDatabase("company");
    public static MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");

    public void insertOne() throws Exception{
        Document data = new Document("name", "Peto").append("age", 12).append("status", "enchanted");
        mongoCollection.insertOne(data);
        mongoClient.close();
        System.out.println("success");
    }

    public void insertMany() throws Exception{
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

    public void print() throws Exception{
        try (MongoCursor<Document> mongoCursor = mongoCollection.find().iterator()) {
            while (mongoCursor.hasNext()) {
                System.out.println(mongoCursor.next().toJson());
            }
        }
    }

    public void udate() throws Exception{
        mongoCollection.updateOne(Filters.eq("name", "Peto"), Updates.set("name","Petko"));
        System.out.println("success");
    }

    public void delete() throws Exception{
        mongoCollection.deleteOne(Filters.eq("name", "Petko"));
        System.out.println("success");
    }
}
