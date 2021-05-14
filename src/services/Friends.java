package services;

import java.sql.SQLException;

import org.json.JSONObject;

import tools.ErrorTool;
import tools.UserTool;
import tools.FriendsTool;

//import org.json.JSONObject;

/**
 * Classe services.Friends, permet la gestion des amis
 * @author Clement
 * Classe qui interagit avec une base de donnees MongoDB
 * Son utilisation intervient apres la connexion d'un utilisateur
 */
public class Friends {
	
	/* Tests :
	 * - les logs ne sont pas null
	 * - l'ami existe
	 * - l'utilisateur et l'ami existent
	 * - l'utilisateur est connecte
	 * Exception :
	 * - une SQLException
	 * Optimisation :	
	 * - gerer l'ouverture et la fermeture de la connexion a la BD SQL
	 * directement dans la methode : ouvrir la connexion avant le test
	 * et fermer la connexion juste apres le deuxieme test
	 */
	public static JSONObject addFriend(String logUsr, String logFriend) {
		try {
			if (logUsr == null || logFriend == null) {
				return ErrorTool.serviceRefused("Erreur d'arguments", 1);
			}
			boolean user_exist = UserTool.userExist(logUsr);
			if (! user_exist) {
				return ErrorTool.serviceRefused("Utilisateur inexistant", 1);
			}
			boolean friend_exist = UserTool.userExist(logFriend);
			if (! friend_exist) {
				return ErrorTool.serviceRefused("Ami inexistant", 1);
			} if (! UserTool.etatConnexion(logUsr)) {
				return ErrorTool.serviceRefused("L'utilisateur est deconnecte", 10);
			}
			if (FriendsTool.areAlreadyFriends(logUsr, logFriend)) {
				return ErrorTool.serviceRefused("Ils sont deja coupaings !", 12);
			}
			int key = FriendsTool.newFriend(logUsr, logFriend);
			return ErrorTool.serviceAccepted(logFriend+" ajoute a la liste", key);
		} catch (SQLException se) {
			return ErrorTool.serviceRefused("Erreur SQL", 1000);
		}
	}
	
	/*
	 * Hypotheses :
	 * - si l'ami a ete ajoute a la liste, c'est qu'il existe
	 * - l'ami est deja dans la liste d'amis
	 * Tests :
	 * - les logins ne sont pas null
	 * - l'utilisateur appelant est connecte
	 * Exception :
	 * - MongoDBException (si ca existe)
	 */
	public static JSONObject deleteFriend(String logUsr, String logFriend) {
		try {
			if (logUsr == null || logFriend == null) {
				return ErrorTool.serviceRefused("Erreur d'arguments", 1);
			} if (! UserTool.etatConnexion(logUsr)) {
				return ErrorTool.serviceRefused("L'utilisateur est deconnecte", 10);
			}
			FriendsTool.deleteFriend(logUsr, logFriend);
			return ErrorTool.serviceAccepted("Ami supprime", 0);
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorTool.serviceRefused(e.toString(), -135);
		}
	}
	
	/*
	 * Test :
	 * - le log de l'utilisateur n'est pas null
	 * Exception :
	 * - certainement une MongoDBException
	 */
	public static JSONObject listFriends(String logUsr) {
		try {
			if (logUsr == null) {
				return ErrorTool.serviceRefused("Erreur d'arguments", 1);
			} if (! UserTool.etatConnexion(logUsr)) {
				return ErrorTool.serviceRefused("L'utilisateur est deconnecte", 10);
			}
			FriendsTool.listOfFriends(logUsr);
			return ErrorTool.serviceAccepted("Liste des amis", 0);
		} catch(SQLException e) {
			return ErrorTool.serviceRefused("Erreur SQL : tu n'as pas d'amis\n"+e.getMessage(), -126);
		}
	}
	
}
