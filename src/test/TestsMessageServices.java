package test;

import org.json.JSONObject;

public class TestsMessageServices {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/* Test Creation Message */
		JSONObject creation = services.Message.createMessage("untare", "C est moi le fifou du coin");
		System.out.println(creation);
		
		/* Test Suppression Message */
		JSONObject suppression = services.Message.deleteMessage("untare", "");
		System.out.println(suppression);
		
		/* Test Liste des Messages*/
		JSONObject liste = services.Message.listMessage();
		System.out.println(liste);
		
	}

}
