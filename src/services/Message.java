package services;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;

//import java.util.List;

import org.json.JSONObject;

import tools.ErrorTool;
import tools.MessageTool;
import tools.UserTool;

/**
 * Classe services.Message, permet la gestion des messages 
 * @author Clement
 * Classe qui interagit avec la base de donnees MongoDB
 * Son utilisation intervient lorsqu'un utilisateur a reussi
 * a creer son compte et � se connecter au site
 */
public class Message {
	
	/**
	 * Methode de creation d'un message
	 * Tests :
	 * - test si les arguments ne sont pas vides (�a pourrait tout faire foirer)
	 * - test si l'utilisateur est bel et bien connecte (�a n'a pas l'air, 
	 * mais c'est tellement evident qu'on oublie de le verifier BANDE DE BOLOSS)
	 * @param message : le texte du message a creer
	 * @param logUsr : le login de l'utilisateur qui poste le msg
	 * @return une JSONObject contenant les infos d'erreur si
	 * la creation a echoue, un message de succes sinon
	 * @throws SQLException 
	 */
	public static JSONObject createMessage(String message, String logUsr) {
		try {
			if (message == null || logUsr == null) {
				return ErrorTool.serviceRefused("Erreur d'arguments", 1);
			} if (! UserTool.userExist(logUsr)) {
				return ErrorTool.serviceRefused("Utilisateur inexistant", 1);
			} //int idUsr = UserTool.getUsrId(logUsr);
			//MessageTool.newMessage(message, idUsr);
			
			/* Methode avec le log plus simple a gerer : 
			 * eviter deux connexions consecutives a la JDBC */
			MessageTool.newMessage(message, logUsr);
			return ErrorTool.serviceAccepted("Message insere dans la BD", 0);
		} catch (SQLException sql) {
			sql.printStackTrace();
			return ErrorTool.serviceRefused(sql.getMessage(), 100);
		}
		
	}
	
	/**
	 * Service permettant de supprimer un message
	 * 
	 * ATTENTION : la methode doit verifier que l'utilisateur
	 * est bien celui qui a poste le message pour pouvoir le supprimer
	 * 
	 * SUGGESTION D'AMELIORATION : integrer un statut de compte pour 
	 * les utilisateurs (genre les moderateurs ou superutilisateurs) qui
	 * du coup pourraient avoir "tous les droits" sur les messages
	 * 
	 * UPDATE Factorisation de code : la methode gere la suppression de tous les messages
	 * d'un utilisateur en donnant la valeur null en argument pour la valeur message
	 * 
	 * @param message : le texte du message a supprimer (mais c'est pas optimise)
	 * @param logUsr : le login de l'utilisateur
	 * @return un JSONObject contenant les infos d'erreur si la suppression a echoue
	 * un message de succes avec le nombre de message supprimes sinon
	 */
	public static JSONObject deleteMessage(String message, String logUsr) {
		try {
			if (message == null && logUsr == null) {
				return ErrorTool.serviceRefused("Erreur d'arguments", -1);
			} if (! UserTool.userExist(logUsr)) {
				return ErrorTool.serviceRefused("Utilisateur inexistant", 1);
			} if (message == null) {
				long cpt = MessageTool.removeMessage(logUsr);
				return ErrorTool.serviceAccepted("Messages supprimes", cpt);
			} else {		
				long cpt = MessageTool.removeMessage(message, logUsr);
				return ErrorTool.serviceAccepted("Message supprime", cpt);
			}
		} catch(SQLException sql) {
			/*Bloc pour gerer les exceptions avec SQL*/
			return ErrorTool.serviceRefused(sql.getMessage(), 100);
		}
	}
	
	
	// !!!!! A REECRIRE !!!!!
	/**
	 * 
	 * @return
	 */
	public static JSONObject listMessage() {
		try {
			List<String> userConnected = UserTool.getUserConnected();
			JSONObject json = new JSONObject();
			for (String login : userConnected) {
				json.append(login, MessageTool.getListeMessage(login));
			}
			return json;//ErrorTool.serviceAccepted("Messages recuperes", 0);
		} catch(SQLException sql) {
			sql.printStackTrace();
			return ErrorTool.serviceRefused(sql.getMessage(), 100);
		} catch(JSONException jsonEx) {
			jsonEx.printStackTrace();
			return ErrorTool.serviceRefused(jsonEx.getMessage(), 10);
		}
	}
	
	/*
	 * Surcharge de la methode precedente avec pour argument le login de l'utilisateur
	 * DU COUP : PAS BESOIN DE TESTER SI L'ARGUMENT EST NULL !
	 * ET : on se fout de savoir si l'utilisateur qui a poste les messages est connecte
	 * A SAVOIR : c'est plus simple de gerer le tri des messages directement dans la
	 * "requete" au format MongoDB directement dans l'utilitaire, c'est pour �a qu'on
	 * reecrit entierement la methode -> �a evite de devoir stocker un nombre enorme
	 * de messages en gros dans le "cache" de l'application et pour chaque demande
	 */
	public static JSONObject listMessage(String login) {
		try {
			JSONObject json = new JSONObject();
			json.append(login, MessageTool.getListeMessage(login));
			return json;
		} catch(JSONException je) {
			je.printStackTrace();
			return ErrorTool.serviceRefused(je.getMessage(), 10);
		}
	}
	
}
