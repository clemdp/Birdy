package test;

import java.sql.SQLException;

import services.User;
import tools.UserTool;

public class TestAuth {
	
	public static void main(String[]args) {
		
		try {
			System.out.println(User.createUser("fifo", "plop", "lop", "p"));
			int usrId = UserTool.getUsrId("fifo", "");
			int t1 = UserTool.insertSession(usrId);
			System.out.println("Ouverture session : "+t1);
			//System.out.println(User.login("plop", "plop"));
			//System.out.println(User.logout("plop"));
			Thread.sleep(100);
			int t2 = UserTool.closeSession(usrId);
			System.out.println("Fermeture session : "+t2);
			System.out.println(User.removeUser("plop", "plop"));
			System.out.println(User.removeUser("fifo", "plop"));
		} catch (SQLException sql) {
			sql.printStackTrace();
			//System.out.println(sql.getMessage());
		} catch(InterruptedException ie) {
			System.out.println("Boulet !");
		}
		
	}
}
