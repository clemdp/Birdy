package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class User
 */
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//PrintWriter pw = response.getWriter();
		//pw.println("je suis en vie !");
		this.getServletContext().getRequestDispatcher("/Enregistrement.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String login = request.getParameter("test");
		System.out.println("Dans servlet : Login = "+login);
		String usrPsw = request.getParameter("mot de passe");
		String usrNom = request.getParameter("nom");
		String usrPrenom = request.getParameter("prenom");
		JSONObject json = services.User.createUser(login, usrPsw, usrNom, usrPrenom);
		PrintWriter pw = response.getWriter();
		pw.println(json);
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("username");
		String password = request.getParameter("mot de passe");
		
		JSONObject json = services.User.removeUser(login, password);
		PrintWriter pw = response.getWriter();
		pw.println(json);
	}

}
