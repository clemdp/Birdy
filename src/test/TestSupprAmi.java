package test;

//import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
//import java.util.ArrayList;
import org.json.JSONObject;

//import tools.FriendsTool;
//import tools.UserTool;

public class TestSupprAmi {
	public static void main(String[]args) {
		List<Integer> liste;
		try {
			liste = tools.UserTool.getListUserSession(true);
			if(liste.size() == 0) {
				System.out.println("Aucun user connecte");
			}
			for(int log : liste) {
				System.out.println(log);
			}
		
			String logUsr = "toi";
			String logFriend = "moi";
			JSONObject json = services.Friends.deleteFriend(logUsr, logFriend);
			
			System.out.println(json);
		} catch(SQLException s) {
			s.printStackTrace();
		}
	}
}
