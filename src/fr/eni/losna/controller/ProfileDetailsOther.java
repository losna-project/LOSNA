package fr.eni.losna.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.losna.bll.ProductManager;
import fr.eni.losna.bo.User;

@WebServlet("/ProfileDetailsOther")
public class ProfileDetailsOther extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductManager productManager = new ProductManager();
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("loggedInUser") == null) {
			request.getServletContext().getRequestDispatcher("/Login").forward(request, response);
		} else {
			
			//check if logged in user is the administrator
			User userDataS = (User) request.getSession().getAttribute("loggedInUser");
			int administrateur = userDataS.getAdministrateur();			
			request.setAttribute("status", administrateur);
			
			//get other user's data from database
			int no_utilisateur = Integer.parseInt(request.getParameter("user_id"));
			User userData = productManager.selectUser(no_utilisateur);
			request.setAttribute("userData", userData);
			request.getServletContext().getRequestDispatcher("/WEB-INF/profile_details_other.jsp").forward(request,
					response);
		}
	}
}
