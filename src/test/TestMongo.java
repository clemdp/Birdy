package test;

import BD.Database;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestMongo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MongoDatabase db = Database.getMongoDBConnection();
		MongoCollection<Document> coll = db.getCollection("Message");
		Document doc = new Document();
		doc.append("id_author", "12");
		doc.append("name", "pierre");
		coll.insertOne(doc);
		
	}

}
