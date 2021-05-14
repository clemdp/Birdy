package BD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import java.util.List;

public class ConnexionBD {
	
	private static Connection connect;
	
	/**
	 * Methode permettant de savoir si la connection a la base de donnees à reussie
	 * @return true si la connexion a reussi, false sinon
	 */
	public static boolean connexionReussie() {
		try {
			connect = Database.getMySQLConnection();
			return true;
		} catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
			System.out.println("Erreur de connection SQL");
			return false;
		}
	}
	
	/**
	 * Getteur sur la connection etablie
	 * @return la Connection etablie
	 */
	public static Connection getConnection() {
		return connect;
	}
	
	/**
	 * Methode de deconnexion : ferme la connection a la BD
	 */
	public static void deconnexion() {
		try {
			connect.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Methode de deconnexion : ferme la connection a la BD
	 * et ferme egalement les objets de communication avec
	 * la BD passes en arguments
	 * @param list
	 */
	public static void deconnexion(Object[] list) {
		try {
			for (Object o : list) {
				if (o instanceof Statement) {
					Statement st = (Statement) o;
					st.close();
				} if (o instanceof ResultSet) {
					ResultSet rs = (ResultSet) o;
					rs.close();
				}
			} connect.close();
		} catch(SQLException se) {
			se.printStackTrace();
		}
	}
	
	
	
}
