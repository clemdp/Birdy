package test;

import tools.FriendsTool;
import tools.UserTool;

import java.sql.SQLException;

import services.User;

public class TestFriendsTool {
	public static void main(String[]args) {
		System.out.println(User.createUser("moi", "1254", "dupont", "florent"));
		System.out.println(User.createUser("toi", "toto", "quiroule", "pierre"));
		
		try {
			int moiId = UserTool.getUsrId("moi", "1254");
			int toiId = UserTool.getUsrId("toi", "");	
			/* TEST FriendsTool.areAlreadyFriends() */
			System.out.println("Sont amis : "+FriendsTool.areAlreadyFriends(moiId, toiId));

			/* TEST FriendsTool.newFriends() */	
			System.out.println("Test ajout (user existants) : "+FriendsTool.newFriend(moiId, toiId));
			System.out.println("Test ajout (ami inexistant) : "+FriendsTool.newFriend(moiId, 0));
			System.out.println("Test ajout (users inexistants) : "+FriendsTool.newFriend(0, 0));
			System.out.println("Test ajout (ami = user) : "+FriendsTool.newFriend(moiId, moiId));
			
			/* TEST FriendsTool.listOfFriends()*/	
			System.out.println("Test list ami (user existant): "+FriendsTool.listOfFriends(moiId).toString());
			System.out.println("Test list ami (user inexistant) : "+FriendsTool.listOfFriends(0));
			System.out.println("Test list ami (user inexistant) : "+FriendsTool.listOfFriends(-12));
			
			System.out.println("Test date (amitie existante) : "+FriendsTool.getDateAjout(moiId, toiId));
			System.out.println("Test date (amitie avec user inexistant) : "+FriendsTool.getDateAjout(moiId, 0));
			System.out.println("Test date (amitie inexistante) : "+FriendsTool.getDateAjout(0, toiId));

			System.out.println("Suppression de l'amitié (existante) : "+FriendsTool.deleteFriend(moiId, toiId));
			System.out.println("Suppresion amitie (inexistante) : "+FriendsTool.deleteFriend(3000, toiId));	
		
			System.out.println("FIN TEST");
		
		} catch(SQLException se) {
			System.out.println("Probleme avec SQL");
			se.printStackTrace();
			
		}
	}
}
