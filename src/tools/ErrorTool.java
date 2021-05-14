package tools;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorTool {
	
	/**
	 * Utilitaire permettant de creer un JSONObject contenant les informations
	 * correspondant a une erreur lors de l'appel à un service
	 * @param message : le message a inserer dans le JSONObject
	 * @param codeErreur : correspond au type d'erreur recontre
	 * @return JSONObject
	 */
	public static JSONObject serviceRefused(String message, long codeErreur) {
		try {
			JSONObject jobj = new JSONObject();
			jobj.put(message, codeErreur);
			return jobj;
		} catch(JSONException jse) {return null;}
	}
	
	/**
	 * Utilitaire permettant de creer un JSONObject contenant les informations
	 * associees a la reussite lors de l'appel à un service
	 * @param message : le message a inserer dans le JSONObject
	 * @param code : correspond a la cle associee
	 * @return JSONObject
	 */
	public static JSONObject serviceAccepted(String message, long code) {
		try {
			JSONObject jobj = new JSONObject();
			jobj.put(message, code);
			return jobj;
		} catch(JSONException jse) {return null;}
		
	}
}
