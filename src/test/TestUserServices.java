package test;

import org.json.JSONObject;

import services.User;

public class TestUserServices {

	public static void main(String[]args) {
		JSONObject json = User.createUser("toto", "000", "Albin", "Michel");
		System.out.println(json);
		
		
	}
	
}
