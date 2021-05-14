package test;

import tools.MessageTool;

public class TestMessageTool {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Creation Message : "+MessageTool.newMessage("patate douce", "JE SUIS UN FOU ! "));
		System.out.println("Liste Message : "+MessageTool.getListMessageId("patate douce"));
		
	}

}
