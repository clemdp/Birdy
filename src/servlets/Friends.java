package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class Friends
 */
public class Friends extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Friends() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String logUsr = request.getParameter("logUsr");
		JSONObject json = services.Friends.listFriends(logUsr);
		PrintWriter pr = response.getWriter();
		pr.println(json);
		//pr.println("Servlet Friends : doGet()");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String logUsr = request.getParameter("logUsr");
		String logFriend = request.getParameter("logFriend");
		JSONObject json = services.Friends.addFriend(logUsr, logFriend);
		PrintWriter pr = response.getWriter();
		pr.println(json);
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String logUsr = request.getParameter("logUsr");
		String logFriend = request.getParameter("logFriend");
		JSONObject json = services.Friends.deleteFriend(logUsr, logFriend);
		PrintWriter pr = response.getWriter();
		pr.println(json);
	}

}
