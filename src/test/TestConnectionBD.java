package test;

import java.sql.Connection;
import java.sql.SQLException;

import BD.Database;

public class TestConnectionBD {
	
	public static void main(String[]args) {
		try {
			Connection c = Database.getMySQLConnection();
			if (c != null)
				System.out.println("Connection BD reussie");
		} catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
			System.out.println("Erreur de connection SQL");
		}
		
		
	}
	
}
