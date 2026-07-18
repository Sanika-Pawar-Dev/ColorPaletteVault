import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class DatabaseConfig {
    private static MongoClient client = null;
    private static MongoDatabase db = null;

    public static MongoDatabase getDatabase() {
        if (db == null) {
            try {
                client = new MongoClient("localhost", 27017);
                db = client.getDatabase("ColorVault");
            } catch (Exception e) {
                System.out.println("DB Connection Fault: " + e.getMessage());
            }
        }
        return db;
    }

    public static MongoCollection<Document> getUsersCollection() {
        return getDatabase().getCollection("users");
    }

    public static MongoCollection<Document> getPalettesCollection() {
        return getDatabase().getCollection("palettes");
    }
}