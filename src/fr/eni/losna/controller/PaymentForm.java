package fr.eni.losna.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.losna.bll.ProductManager;
import fr.eni.losna.bll.UserManager;
import fr.eni.losna.bo.User;

@WebServlet("/PaymentForm")
public class PaymentForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("messagepayment", "");
		request.getServletContext().getRequestDispatcher("/WEB-INF/payment_form.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductManager productManager = new ProductManager();

		User userDataS = (User) request.getSession().getAttribute("loggedInUser");
		int no_utilisateur = userDataS.getNo_utilisateur();
		String nom_utilisateur = userDataS.getNom();
		String prenom_utilisateur = userDataS.getPrenom();
		int credits = (Integer) request.getSession().getAttribute("credits");
		int cost = (Integer) request.getSession().getAttribute("cost");
		String card = (String) request.getParameter("card");
		String name = (String) request.getParameter("name");
		String checkName = nom_utilisateur + " " + prenom_utilisateur;

		if (UserManager.validateCreditCard(card)) {

			if (checkName.equals(name)) {

				int userCredit = productManager.selectUser(no_utilisateur).getCredit();
				productManager.setCredit(credits + userCredit, no_utilisateur);

				userDataS.setCredit(credits + userCredit);
				request.getSession().setAttribute("loggedInUser", userDataS);
				request.getSession().removeAttribute("credits");
				request.getSession().removeAttribute("cost");

				request.setAttribute("messageupdate", "Achat réussi");
				request.getServletContext().getRequestDispatcher("/ProfileDetailsUser").forward(request, response);

			} else {

				request.setAttribute("messagepayment", "Please enter a valid name");
				request.getServletContext().getRequestDispatcher("/WEB-INF/payment_form.jsp").forward(request,
						response);
			}

		} else {
			request.setAttribute("messagepayment", "Please enter a valid credit card number");
			request.getServletContext().getRequestDispatcher("/WEB-INF/payment_form.jsp").forward(request, response);
		}
	}
}
