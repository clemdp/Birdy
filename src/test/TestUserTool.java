package test;

import java.sql.SQLException;

import tools.FriendsTool;
import tools.UserTool;

public class TestUserTool {
	private static String login = "Patoua", nom = "Mais", prenom = "Moi";
	public static void main(String[]args) {
		try {
			System.out.println("Creation User : "+UserTool.creationUser(login, "Vilain", nom, prenom));
			System.out.println("User Exist : "+UserTool.userExist(login));
			System.out.println("User Exit (pas) : "+UserTool.userExist("nope"));

			System.out.println("\nTEST PASSWORD:");
			int usrId = testPassword();
			System.out.println("\nTEST SESSION :");
			testSession(usrId);
			System.out.println("\nTEST SUPPRESSION :");
			testSuppression(usrId);
			System.out.println("FIN TEST");
		} catch(SQLException sql) {
			sql.printStackTrace();
		} catch(InterruptedException ie) {
			ie.printStackTrace();
		}
		
	}
	
	
	public static int testPassword() throws SQLException{
		System.out.println("Test Password 1 : "+UserTool.checkPsw(login, "Vilain"));
		System.out.println("Test Password 2 : "+UserTool.checkPsw(login, "Ndezaefr"));
		System.out.println("Test Password 3 : "+UserTool.checkPsw("nope", ""));
		System.out.println("Test Password 4 : "+UserTool.checkPsw("nope", null));
		System.out.println("Test Password 5 : "+UserTool.checkPsw(null, null));
		String psw = UserTool.getPassword(login, nom, prenom);
		System.out.println("Get Password : "+psw);
		System.out.println("Get UsrId (utilisateur inexistant): "+UserTool.getUsrId("nope", ""));
		int usrId = UserTool.getUsrId(login, psw);
		System.out.println("Get UsrId (sans mdp) : "+UserTool.getUsrId(login, ""));
		System.out.println("Get UsrId (mdp errone) : "+UserTool.getUsrId(login, "deafafer"));
		System.out.println("Get UsrId (avec mdp) : "+usrId);
		System.out.println("Get usrLogin : "+UserTool.getUsrLogin(usrId));
		return usrId;
	}
	
	public static void testSession(int usrId) throws SQLException, InterruptedException{
		System.out.println("Insert Session : "+UserTool.insertSession(usrId));
		System.out.println("Etat Connexion : "+UserTool.etatConnexion(usrId));
		System.out.println("Updated on : "+UserTool.getDateUpdate(usrId));
		Thread.sleep(1000);
		System.out.println("Close Session : "+UserTool.closeSession(usrId));
		System.out.println("Etat Connexion : "+UserTool.etatConnexion(usrId));
		System.out.println("Updated on : "+UserTool.getDateUpdate(usrId));
		
		System.out.println("\nList User : "+UserTool.getListUser());
		System.out.println("List User Connected : "+UserTool.getListUserSession(true));
		System.out.println("List User Unconnected : "+UserTool.getListUserSession(false));
	}
	
	public static void testSuppression(int usrId) throws SQLException{
		UserTool.creationUser("plop", "plop", "ll", "pp");
		UserTool.creationUser("titi", "ferfer", "tdze", "dezf");
		int plopId = UserTool.getUsrId("plop", "");
		int titiId = UserTool.getUsrId("titi", "ferfer");
		FriendsTool.newFriend(plopId, usrId);
		FriendsTool.newFriend(usrId, plopId);
		FriendsTool.newFriend(titiId, usrId);
		FriendsTool.newFriend(usrId, titiId);
		FriendsTool.newFriend(plopId, titiId);
		System.out.println("Amis de "+login+" : "+FriendsTool.listOfFriends(usrId));
		System.out.println("Amis de plop : "+FriendsTool.listOfFriends(plopId));
		System.out.println("Suppression Complete User : "+UserTool.suppressionUser(usrId, true));
		System.out.println("Amis de plop : "+FriendsTool.listOfFriends(plopId));
		System.out.println("Amis de titi : "+FriendsTool.listOfFriends(titiId));
		System.out.println("Suppression Complete User : "+UserTool.suppressionUser(plopId, false));
		System.out.println("Suppression Complete User : "+UserTool.suppressionUser(titiId, true));
	}
	
}
