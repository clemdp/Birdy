package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import BD.ConnexionBD;

/**
 * 
 * @author Clement DUPUY et Maxence LOZACH
 *
 */
public class UserTool {
	
	private static String colUsrSession = "usrId";
	private static String colUsrUser = "idU";
	
/***** UTILITAIRES DE GESTION DE LA TABLE USER *****/	
	/**
	 * Utilitaire de creation d'un utilisateur
	 * @param login : le login du compte utilisateur
	 * @param mdp : le mot de passe associe
	 * @param nom : le nom de l'utilisateur
	 * @param prenom : le prenom de l'utilisateur
	 * @return le nombre de lignes ajoutees dans la BD
	 * @throws SQLException
	 */
	public static int creationUser(String login, String mdp, String nom, String prenom) throws SQLException{
		if (userExist(login)) {
			System.out.println("Le login : "+login+" existe deja !");
			return -1;
		}
		boolean b = ConnexionBD.connexionReussie();
		if (! b) {
			System.out.println("Erreur de connection BD");
			return -1;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		
		String update = "INSERT INTO User(login, mdp, nom, prenom) VALUES ('"+login+"', '"+mdp+"', '"+nom+"', '"+prenom+"')";
		int i = st.executeUpdate(update);
		
		st.close();
			
		ConnexionBD.deconnexion();
		return i;
	}
	
	/**
	 * Utilitaire de suppression d'un compte utilisateur a partir de son identifiant
	 * @param id : l'id de l'utilisateur
	 * @param complet : selectionne une suppression complete (tables Session, Friends, User)
	 * @return le nombre de lignes supprimees
	 * @throws SQLException
	 */
	public static int suppressionUser(int usrId, boolean complet) throws SQLException{
		boolean b = ConnexionBD.connexionReussie();
		if (! b) {
			System.out.println("Erreur de connection BD");
			return -1;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		int i = 0;
		if(complet) {
			String delete = "DELETE FROM Session WHERE "+colUsrSession+" = '"+usrId+"'";
			i += st.executeUpdate(delete);
			
			delete = "DELETE FROM Friends WHERE usrId = '"+usrId+"' OR friendId = '"+usrId+"'";
			i += st.executeUpdate(delete);
		}
		
		String delete = "DELETE FROM User WHERE "+colUsrUser+" = '"+usrId+"'";
		i += st.executeUpdate(delete);
		
		st.close();
		ConnexionBD.deconnexion();
		return i;
	}
	
	/**
	 * Utilitaire de verification de l'existance du login dans la BD
	 * @param login : le login a verifier
	 * @return true : si le login est deja existant
	 * @throws SQLException
	 */
	public static boolean userExist(String login) throws SQLException{
		boolean b = ConnexionBD.connexionReussie();
		if (! b) {
			System.out.println("Erreur de connection BD");
			return false;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		ResultSet rs = st.executeQuery("SELECT login FROM User WHERE login = '"+login+"'");
		boolean exist = rs.next();
		
		st.close();
		return exist;
	}
	
	/**
	 * Utilitaire de verification du mot de passe
	 * @param login : le login associe au mot de passe
	 * @param pwd : le mot de passe a verifier
	 * @return true : si pwd correspond au mot de passe associe au login
	 * @throws SQLException
	 */
	public static boolean checkPsw(String login, String pwd) throws SQLException{
		boolean b = ConnexionBD.connexionReussie();
		if (!b) {
			System.out.println("Erreur de connection BD");
			return false;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM User WHERE login = '"+login+"' AND mdp = '"+pwd+"'");
		boolean ok = rs.next();
		rs.close();
		st.close();
		c.close();
		return ok;
	}
	
	/**
	 * Utilitaire de récupération de mot de passe
	 * @param login : login de l'utilisateur
	 * @param nom : nom de l'utilisateur
	 * @param prenom : prenom de l'utilisateur
	 * @return mdp : le mot de passe associé au compte
	 * @throws SQLException
	 */
	public static String getPassword(String login, String nom, String prenom) throws SQLException{
		boolean b = ConnexionBD.connexionReussie();
		if (!b) {
			System.out.println("Erreur de connection BD");
			return null;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		ResultSet rs = st.executeQuery("SELECT mdp FROM User WHERE login = '"+login
				+"' AND prenom = '"+prenom+"' AND nom = '"+nom+"'");
		String mdp = "";
		if (rs.next()) {
			mdp = rs.getString("mdp");
		}
		rs.close();
		st.close();
		c.close();
		return mdp;
	}
	
	/**
	 * Utilitaire de recuperation de l'id d'un utilisateur
	 * @param log : le login de User
	 * @param psw : le mot de passe associe
	 * @return usrId : l'identifiant de l'User
	 * @throws SQLException
	 */
	public static int getUsrId(String log, String psw) throws SQLException{
		boolean b = ConnexionBD.connexionReussie();
		if (!b) {
			System.out.println("Erreur de connection BD");
			return -1;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		int usrId = 0;
		if (psw == "") {
			ResultSet rs = st.executeQuery("SELECT "+colUsrUser+" FROM User WHERE login = '"+log+"'");
			if (rs.next()) {
				usrId = rs.getInt("idU");
			}
		} else {
			ResultSet rs = st.executeQuery("SELECT idU FROM User WHERE login = '"+log+"' AND mdp = '"+psw+"'");
			if (rs.next()) {
				usrId = rs.getInt("idU");
			}
		}
		//rs.close();
		st.close();
		c.close();
		return usrId;
	}
	
	/**
	 * Utilitaire permettant de recuperer le login d'un utilisateur a partir de son usrId
	 * @param usrId : l'id de l'utilisateur dans la base
	 * @return String : le login de l'utilisateur
	 * @throws SQLException
	 */
	public static String getUsrLogin(int usrId) throws SQLException{
		boolean b = ConnexionBD.connexionReussie();
		if (! b) {
			System.out.println("Erreur de connection BD");
			return "";
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		ResultSet rs = st.executeQuery("SELECT login FROM User WHERE "+colUsrUser+"= '"+usrId+"'");
		String login = "";
		if (rs.next()) {
			login = rs.getString("login");
		}
		st.close();
		c.close();
		return login;
		
	}

	/**
	 * Utilitaire de recuperation de la liste des utilisateurs
	 * @return List<Integer> contenant tous les utilisateurs ayant un compte
	 * @throws SQLException
	 */
	public static List<Integer> getListUser() throws SQLException{
		boolean b = ConnexionBD.connexionReussie();
		if (! b) {
			System.out.println("Erreur de connexion BD");
			return null;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		List<Integer> listId = new ArrayList<Integer>();
		ResultSet rs = st.executeQuery("SELECT "+colUsrUser+" FROM User");
		while(rs.next()) {
			int id = rs.getInt(colUsrUser);
			listId.add(id);
		}
		
		st.close();
		c.close();
		return listId;
	}

/***** UTILITAIRES DE GESTION DE LA TABLE SESSION *****/
	
	/**
	 * Utilitaire permettant d'ajouter une connexion
	 * La Date est mise automatiquement a jour
	 * Utilitaire de creation d'une session dans la BD
	 * @param usrId : l'identifiant de l'User qui se connecte au site
	 * @return
	 * @throws SQLException
	 */
	public static int insertSession(int usrId) throws SQLException{
		boolean b = ConnexionBD.connexionReussie();
		int lignes = 0;
		if (! b) {
			System.out.println("Erreur de connection BD");
			return -1;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		String select = "SELECT "+colUsrSession+" FROM Session WHERE "+colUsrSession+" = '"+usrId+"'";
		ResultSet rs = st.executeQuery(select);
		if (!rs.next()) {
			String insertion = "INSERT INTO Session("+colUsrSession+", etat) VALUES ('"+usrId+"', true)";
			lignes = st.executeUpdate(insertion);
		} else {
			String update = "UPDATE Session SET etat = true WHERE "+colUsrSession+" = '"+usrId+"'";
			lignes = st.executeUpdate(update);
		}
		st.close();
		c.close();
		return lignes;
	}
	
	
	/**
	 * Utilitaire de fermeture d'une session
	 * @param usrId : l'identifiant de l'User qui se deconnecte
	 * @return le nombre de lignes inseres dans la table Session
	 * @throws SQLException
	 */
	public static int closeSession(int usrId) throws SQLException{
		boolean b = ConnexionBD.connexionReussie();
		if (! b) {
			System.out.println("Erreur de connection BD");
			return -1;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		String update = "UPDATE Session SET etat = false WHERE "+colUsrSession+" = '"+usrId+"'";
		int lignes = st.executeUpdate(update);
		st.close();
		c.close();
		return lignes;
	}
	
	
	/**
	 * Utilitaire de verification de la connection de l'User
	 * @param usrId : identifiant de User
	 * @return
	 */
	public static boolean etatConnexion(int usrId) throws SQLException{
		boolean b = ConnexionBD.connexionReussie();
		if (! b) {
			System.out.println("Erreur de connection BD");
			return false;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		String requete = "SELECT etat FROM Session WHERE "+colUsrSession+" = '"+usrId+"'";
		ResultSet rs = st.executeQuery(requete);
		boolean etat = false;
		if(rs.next()) {
			etat = rs.getBoolean("etat");
		}
		st.close();
		c.close();
		return etat;
	}
	
	
	/**
	 * Utilitaire de recuperation de la liste des utilisateurs en fonction de leur etat
	 * @param etat : indique si l'on chercher les utilisateurs connectes (true) ou pas
	 * @return List<Integer> contenant l'usrId des utilisateurs selectionness
	 * @throws SQLException
	 */
	public static List<Integer> getListUserSession(boolean etat) throws SQLException{
		boolean b = ConnexionBD.connexionReussie();
		if (! b) {
			System.out.println("Erreur de connexion BD");
			return null;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		List<Integer> listId = new ArrayList<Integer>();
		
		ResultSet rs = st.executeQuery("SELECT "+colUsrSession+" FROM Session WHERE etat = "+etat);
		while(rs.next()) {
			int id = rs.getInt(colUsrSession);
			listId.add(id);
		}
		
		st.close();
		c.close();
		return listId;
	}
	
	
	/**
	 * 
	 * @param usrId
	 * @return
	 * @throws SQLException
	 */
	public static Timestamp getDateUpdate(int usrId) throws SQLException{
		boolean b = ConnexionBD.connexionReussie();
		if (!b) {
			System.out.println("Erreur de connexion BD");
			return null;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		String requete = "SELECT date FROM Session WHERE usrId = '"+usrId+"'";
		ResultSet rs = st.executeQuery(requete);
		
		Timestamp date = null;
		if (rs.next()) {
			date = rs.getTimestamp("date");
		}
		
		st.close();
		c.close();
		
		return date;
	}
	
}
