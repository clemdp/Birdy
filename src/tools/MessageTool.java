package tools;

import java.util.List;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MessageTool {
	
	private static MongoDatabase mongo = BD.Database.getMongoDBConnection();
	private static MongoCollection<Document> collection = mongo.getCollection("Message");
	
	/**
	 * Utilitaire permettant de tester si le message consulte 
	 * appartient a l'utilisateur qui veut y acceder
	 * Hypothese : l'utilisateur existe dans la BD
	 * @param usrId : identifiant de l'utilisateur
	 * @return true : si le message appartient a l'utilisateur
	 * @throws SQLException 
	 */
	public static boolean isMessageToUser(String login, String text) throws SQLException {
		
		if (collection != null) {
			System.out.println("Acces a collection Message, MongoDB");
		}
		
		Document query = new Document();
		//query.append("usrId", usrId);
		query.append("login", login);
		query.append("text", text);
		//FindIterable<Document> ite = collection.find(query);
		Object res = collection.find(query);
		//long nbMsg = ;
		
		return res != null;
	}
	
	/**
	 * Utilitaire permettant de creer un nouveau message
	 * utilise le login de l'utilisateur
	 * @param text : le texte du message
	 * @param login : le login de l'utilisateur
	 * @return
	 */
	public static int newMessage(String login, String text) {
		if (collection != null) {
			System.out.println("Acces a collection Message, MongoDB");
		}
		
		Document query = new Document();
		query.append("login", login);
		query.append("date", new Timestamp(System.currentTimeMillis()));
		query.append("text", text);
		collection.insertOne(query);
		return 0;
	}
	
	public static List<Integer> getListMessageId (String login){
		//ListIndexesIterable<Document> ite = collection.listIndexes(Class.forName("String"));
		List<Integer> li = new ArrayList<Integer>();
		FindIterable<Document> ite = collection.find(new Document("login", login));
		for(Document d : ite) {
			li.add(d.getInteger("_id"));
		}
		return li;
	}
	
	/**
	 * Utilitaire permettant de retirer un message
	 * utilise le login du createur du message (utile pour la gestion de la BD)
	 * @param text : le texte contenu du message 
	 * @param login : le login de l'utilisateur qui l'a poste
	 * @return le nombre de messages supprimes (theoriquement = 1)
	 */
	public static long removeMessage(String login, String date) {
		Document doc = new Document();
		//doc.append("text", text);
		doc.append("login", login);
		DeleteResult res = collection.deleteOne(doc);
		return res.getDeletedCount();
	}
	
	/**
	 * Utilitaire permettant de retirer un message 
	 * a partir de l'id attribue par defaut par MongoDB 
	 * @param id : identifiant du message (Document Mongo)
	 * @return le nombre de messages supprimes (theoriquement = 1)
	 */
	public static long removeMessage(Integer id) {
		DeleteResult res = collection.deleteOne(new Document("id", id));
		return res.getDeletedCount();
	}
	
	/**
	 * Utilitaire supprimant tous les messages d'un utlisateur donné
	 * Pratique quand on supprime un utilisateur !
	 * @param login : le login de l'utilisateur
	 * @return le nombre de messages supprimés
	 */
	public static long removeMessage(String login) {
		DeleteResult res = collection.deleteMany(new Document("login", login));
		
		return res.getDeletedCount();
	}
	
	/*
	 * J'ai ecrit des types de base, mais j'crois que normalement ca renvoie
	 * un objet JSONObject
	 */
	public static List<Document> getListeMessage(String login) {

		Document query = new Document();
		query.append("login", login);
		
		FindIterable<Document> ite = collection.find(query);
		//Document listM = ite.value("")
		List<Document> d = new ArrayList<Document>();
		
		List<Document> listMessage = new ArrayList<Document>();
		for(Document doc : ite) {
			d.add(doc);
		}
		
		for (Document doc : d) {
			doc.remove("_id");
			listMessage.add(doc);
		}
		
		return listMessage;
	}
	
}
