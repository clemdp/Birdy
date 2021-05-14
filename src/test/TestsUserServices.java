package test;

import org.json.JSONObject;

public class TestsUserServices {
	
	public static void main(String[]args) {
		/* Test createUser */
		JSONObject json = services.User.createUser("toto", "135", "tata", "titi");
		System.out.println("Creation utilisateur : "+json);

		/* Test login */
		JSONObject log = services.User.login("toto", "135");
		System.out.println("Login : "+log);
		
		/* Test logout */
		JSONObject logout = services.User.logout("toto");
		System.out.println("Logout : "+logout);
		
		/* Test removeUser */
		JSONObject json2 = services.User.removeUser("toto", "135");
		System.out.println("Suppression utilisateur : "+json2);
	
	}
}
