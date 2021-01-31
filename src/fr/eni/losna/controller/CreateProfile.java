package fr.eni.losna.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.losna.bll.UserManager;
import fr.eni.losna.bo.User;

@WebServlet("/CreateProfile")
public class CreateProfile extends HttpServlet {
	static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().setAttribute("message", "");
		request.getServletContext().getRequestDispatcher("/WEB-INF/create_profile.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// retrieve data from the form and register in database
		User userDataS = new User();
		
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String code_postal = request.getParameter("code_postal");
		String ville = request.getParameter("ville");
		String mot_de_passe = request.getParameter("mot_de_passe");

		userDataS = UserManager.register(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe);
		if (userDataS != null) {
			request.getSession().setAttribute("loggedInUser", userDataS);
			request.getServletContext().getRequestDispatcher("/AuctionList").forward(request, response);
		} else {
			request.getSession().setAttribute("message", "Registration failed. Username or email already registered");
			request.getServletContext().getRequestDispatcher("/WEB-INF/create_profile.jsp").forward(request, response);
		}
	}
}
