package tools;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import BD.ConnexionBD;
//import BD.Database;

public class FriendsTool {
	
	/**
	 * Utilitaire qui ajoute un couple d'amis de maniere unilaterale
	 * En effet, on ne peut pas forcer un utilisateur a nous ajouter dans ses amis
	 * Equivalent de l'option "suivre" d'Instagram (par exemple)
	 * @param usrId : l'identifiant utilisateur qui fait la demande d'ajout
	 * @param frId : l'identifiant utilisateur cible
	 * @return le nombre de lignes ajoutees dans la BD
	 * @throws SQLException
	 */
	public static int newFriend(int usrId, int frId) throws SQLException{
		if (usrId < 1 || frId < 1) {
			return 0;
		} if (usrId == frId) {
			return 0;
		}
		if (areAlreadyFriends(usrId, frId)) {
			return 0;
		}
		boolean b = ConnexionBD.connexionReussie();
		if (!b) {
			System.out.println("Erreur connexion a la BD");
			return -1;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		
		String update1 = "INSERT INTO Friends(usrId, friendId) Values('"+usrId+"', '"+frId+"')";
		int insertion = st.executeUpdate(update1);
		st.close();
		c.close();
		return insertion;
	}
	
	/**
	 * Utilitaire qui supprime un ami de maniere unilaterale
	 * Meme principe, on peut pas forcer un utilisateur a nous supprimer de ses amis
	 * Equivalent du "Unfollow" de Twitter 
	 * @param usrId : l'identifiant utilisateur qui veut retirer l'ami
	 * @param frId : l'identifiant utilisateur de l'ami a retirer
	 * @return le nombre de lignes supprimees
	 * @throws SQLException
	 */
	public static int deleteFriend(int usrId, int frId) throws SQLException {
		boolean b = ConnexionBD.connexionReussie();
		if (!b) {
			System.out.println("Erreur connexion a la BD");
			return -1;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		
		String delete = "DELETE FROM Friends WHERE usrId = '"+usrId+"' AND friendId = '"+frId+"'";
		int suppression = st.executeUpdate(delete);
		st.close();
		c.close();
		return suppression;
		
	}
	
	/**
	 * Utilitaire qui permet de recuperer la liste des amis d'un utilisateur
	 * @param logUsr : l'identifiant de l'utilisateur souhaitant acceder a sa liste d'amis
	 * @return la liste de Identifiants des amis de l'utilisateur
	 * @throws SQLException
	 */
	public static List<Integer> listOfFriends(int usrId) throws SQLException{
		boolean b = ConnexionBD.connexionReussie();
		if (!b) {
			System.out.println("Erreur connexion a la BD");
			return null;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		
		String requete = "SELECT friendId FROM Friends WHERE usrId = '"+usrId+"'";
		ResultSet rs = st.executeQuery(requete);
		List<Integer> listId = new ArrayList<Integer>();
		while(rs.next()) {
			listId.add(rs.getInt("friendId"));
		}
		st.close();
		c.close();
		return listId;
	}
	
	/**
	 * Utilitaire qui permet de verifier si deux utilisateurs sont deja amis
	 * @param logUsr : l'utilisateur voulant ajouter un ami
	 * @param logFrd : l'ami a ajouter
	 * @return True s'ils sont deja amis, False sinon
	 * @throws SQLException
	 */
	public static boolean areAlreadyFriends(int usrId, int frId) throws SQLException{
		boolean b = ConnexionBD.connexionReussie();
		if (!b) {
			System.out.println("Erreur connexion a la BD");
			return false;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
		
		String requete = "SELECT COUNT(*) as nb FROM Friends WHERE usrId = '"+usrId+"' AND friendId = '"+frId+"'";
		ResultSet rs = st.executeQuery(requete);
		
		if (rs.next() && rs.getInt("nb") >= 1) {
			return true;
		}
		rs.close();
		st.close();
		c.close();
		return false;
	}
	
	/**
	 * Utilitaire permettant de recuperer la date de creation de l'amitie
	 * @param logUsr : l'utilisateur ayant ajoute un ami
	 * @param logFrd : l'utilisateur etant ajoute
	 * @return la Data d'ajout de l'ami
	 * @throws SQLException
	 */
	public static Date getDateAjout(int usrId, int frId) throws SQLException{
		boolean b = ConnexionBD.connexionReussie();
		if (!b) {
			System.out.println("Erreur connexion a la BD");
			return null;
		}
		Connection c = ConnexionBD.getConnection();
		Statement st = c.createStatement();
			
		String requete = "SELECT date FROM Friends WHERE usrId = '"+usrId+"' AND friendId = '"+frId+"'";
		ResultSet rs = st.executeQuery(requete);
		Date date = null;
		if (rs.next()) {
			date = rs.getDate("date");
		}
		rs.close();
		st.close();
		c.close();
		return date;
	}
	
}
