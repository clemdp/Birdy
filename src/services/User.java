package services;

import java.sql.SQLException;

import org.json.JSONException;
//import org.json.JSONException;
import org.json.JSONObject;

import tools.*;

public class User {
	
	/**
	 * Service de creation d'un utilisateur
	 * Test les arguments, test l'existance du login dans la base de donnees
	 * @param log : le login de l'utilisateur
	 * @param mdp : le mot de passe associe
	 * @param nom : le nom de l'utilisateur
	 * @param prenom : le prenom de l'utilisateur
	 * @return JSONObject contenant les infos d'erreur si la creation a echoue
	 * le nombre de lignes creees dans la table User si la creation a reussi
	 */
	public static JSONObject createUser(String log, String mdp, String nom, String prenom) {

		try {
			if ((log == null) || (mdp == null) || (nom == null) || (prenom == null)) {
				System.out.println("log = "+log+", mdp = "+mdp+", nom = "+nom+", prenom = "+prenom+";");
				return ErrorTool.serviceRefused("Mauvais argument", -1);
			}
			boolean user_exist = UserTool.userExist(log);
			if (user_exist) { 
				return ErrorTool.serviceRefused("Nom utilisateur deja existant !", -1);
			}
			int key = UserTool.creationUser(log, mdp, nom, prenom);
			return ErrorTool.serviceAccepted("creation utilisateur ok", key);
		} catch(SQLException se) {
			se.printStackTrace();
			return ErrorTool.serviceRefused(se.getMessage(), 1000);
		}
	}
	
	/**
	 * Service de suppression d'un utilisateur
	 * @param log : le login de l'utilisateur
	 * @param pswd : le mot de passe associe
	 * @return JSONObject : contenant les infos d'erreur si la suppresion a echoue
	 * le nombre de lignes supprimees sinon
	 */
	public static JSONObject removeUser(String log, String pswd) {
		try {
			if (log == null || pswd == null) {
				return ErrorTool.serviceRefused("Erreur d'arguments", 1);
			}
			boolean user_exist = UserTool.userExist(log);
			if (! user_exist) {
				return ErrorTool.serviceRefused("Utilisateur inexistant", 1);
			}
			int key = UserTool.suppressionUser(log, pswd);
			return ErrorTool.serviceAccepted("Suppression reussie", key);
		} catch(SQLException se) {
			se.printStackTrace();
			return ErrorTool.serviceRefused(se.getMessage(), 1000);
		}
	}	
	
	/**
	 * Service permettant la connexion d'un User
	 * @param log : le login de User
	 * @param psw : le mot de passe associe
	 * @return un JSONObject contenant les infos d'erreur si la connexion a echoue
	 * la cle associee a la session cree sinon
	 */
	public static JSONObject login(String log, String psw) {
		try {
			if ((log == null) || (psw == null)) {
				System.out.println("Dans services : log = "+log+", mdp = "+psw);
				return ErrorTool.serviceRefused("Mauvais argument", 0);
			}
			boolean is_usr = UserTool.userExist(log);
			if (!is_usr) {
				return ErrorTool.serviceRefused("Utilisateur inexistant", -1);
			}
			boolean psw_ok = UserTool.checkPsw(log, psw);
			if (! psw_ok) {
				System.out.println("password : "+psw);
				return ErrorTool.serviceRefused("Erreur mot de passe", -1);
			}
			int idUsr = UserTool.getUsrId(log, psw);
			int key = UserTool.insertSession(idUsr);
			JSONObject jsobj = ErrorTool.serviceAccepted("Authentification reussie", key);
			//System.out.println("Authentification reussie");
			return jsobj;
		} catch(SQLException se) {
			se.printStackTrace();
			return ErrorTool.serviceRefused(se.getMessage(), 1000);
		}
	}
	
	/**
	 * Service de deconnexion d'un utilisateur
	 * @param idUsr : l'identifiant de l'utilisateur
	 * @return un JSONObject contenant les infos d'erreur si la deconnexion a echoue
	 * la cle associee a la session supprimee sinon
	 * @throws SQLException
	 */
	/*public static JSONObject logout(int idUsr) {
		try {
			if (UserTool.etatConnexion(idUsr) != true) {
				return ErrorTool.serviceRefused("Utilisateur non connecte", -1);
			}
			int key = UserTool.closeSession(idUsr);
			JSONObject jsobj = ErrorTool.serviceAccepted("Deconnexion reussie", key);
			return jsobj;
		} catch(SQLException se) {
			se.printStackTrace();
			return ErrorTool.serviceRefused(se.getMessage(), 1000);
		}
	}*/
	
	public static JSONObject logout(String login) {
		try {
			if (login == null) {
				return ErrorTool.serviceRefused("Mauvais argument", 0);
			} 
			boolean is_usr = UserTool.userExist(login);
			if (!is_usr) {
				return ErrorTool.serviceRefused("Utilisateur inexistant", -1);
			}
			int idUsr = UserTool.getUsrId(login);
			if (UserTool.etatConnexion(idUsr) != true) {
				return ErrorTool.serviceRefused("Utilisateur non connecte", -1);
			}
			int key = UserTool.closeSession(idUsr);
			JSONObject jsobj = ErrorTool.serviceAccepted("Deconnexion reussie", key);
			return jsobj;
		} catch(SQLException se) {
			se.printStackTrace();
			return ErrorTool.serviceRefused(se.getMessage(), 1000);
		}
	}
	
	public static JSONObject getPassword(String login, String nom, String prenom) {
		try {
			if (login == null || nom == null || prenom == null) {
				return ErrorTool.serviceRefused("Veuillez completer tous les champs", -1);
			}
			boolean is_usr = UserTool.userExist(login);
			if (!is_usr) {
				return ErrorTool.serviceRefused("Utilisateur inexistant", -1);
			}
			String mdp = UserTool.getPassword(login, nom, prenom);
			if (mdp == null || mdp.equals("")) {
				return ErrorTool.serviceRefused("Il y a un probleme...", -5);
			}
			JSONObject json = new JSONObject();
			json.put("mdp", mdp);
			return json;
		} catch(SQLException sql) {
			sql.printStackTrace();
			return ErrorTool.serviceRefused(sql.getMessage(), 1000);
		} catch(JSONException jex) {
			jex.printStackTrace();
			return ErrorTool.serviceRefused(jex.getMessage(), 100);
		}
	}
	
}
