package test;

import org.json.JSONException;
import org.json.JSONObject;

import services.*;

public class TestMessageUserServices {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject json = User.createUser("toto", "000", "Albin", "Michel");
		System.out.println(json);
		System.out.println(User.createUser("tata", "ABDC", "Michel", "Bleu"));
		System.out.println(User.login("toto", "000"));
		System.out.println(User.login("tata", "ABDC"));
		System.out.println(User.getPassword("tata", "Michel", "Bleu"));
		System.out.println("Retry connecting : tata");
		try {
			String mdp = User.getPassword("tata", "Michel", "Bleu").getString("mdp");
			System.out.println(User.login("tata", mdp));
		}catch(JSONException jex) {
			jex.printStackTrace();
		}
		
		System.out.println(Message.createMessage("Hello cest moi Toto", "toto"));
		System.out.println(Message.createMessage("Message 2 toto", "toto"));
		System.out.println(Message.createMessage("Salut moi cest Tata", "tata"));
		System.out.println(Message.listMessage());
		System.out.println("Messages de toto : "+Message.listMessage("toto"));
		System.out.println("Messages de tata : "+Message.listMessage("tata"));
		//System.out.println(Message.deleteMessage("Hello cest moi Toto", "toto"));
		System.out.println(Message.deleteMessage(null, "toto"));
		System.out.println(Message.deleteMessage(null, "tata"));
		System.out.println(User.logout("toto"));
		System.out.println(User.logout("tata"));
		
	}

}
