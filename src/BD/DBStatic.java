package BD;

public class DBStatic {
	public static final String mysql_host = "localhost";
	public static final String mysql_user = "root";
	public static final String mysql_db = "serveur_test_duploz";
	public static boolean pooling = false;
	public static final String mysql_password = "";
	
	public static final String mongo_bd = "bd3I017_duploz"; /*nom de la BD MongoDB*/

	public static void setPooling(boolean b) {
		pooling = b;
	}
}
