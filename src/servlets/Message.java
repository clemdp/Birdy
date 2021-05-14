package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class Message
 */
public class Message extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Message() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Fait appel au service de recuperation de la liste des messages : Message.listMessage();
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		this.getServletContext().getRequestDispatcher("/Messages.html").forward(request, response);
		JSONObject json = services.Message.listMessage();
		PrintWriter pr = response.getWriter();
		pr.println(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Fait appel au service de creation d'un nouveau message : services.Message.createMessage(String text, String login);
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String text = request.getParameter("text");
		String login = request.getParameter("login");
		JSONObject json = services.Message.createMessage(text, login);
		PrintWriter pr = response.getWriter();
		pr.println(json);
	}
	
	/**
	 * Fait appel au service de suppression d'un message : services.Message.deleteMessage(String message, String login);
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = request.getParameter("message");
		String login = request.getParameter("login");
		JSONObject json = services.Message.deleteMessage(message, login);
		PrintWriter pr = response.getWriter();
		pr.println(json);
	}
	
}
