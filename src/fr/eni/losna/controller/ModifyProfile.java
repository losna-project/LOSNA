package fr.eni.losna.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.losna.bll.ProductManager;
import fr.eni.losna.bll.UserManager;
import fr.eni.losna.bo.ProductSold;
import fr.eni.losna.bo.User;

@WebServlet("/ModifyProfile")
public class ModifyProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loggedInUser") == null) {
			request.getServletContext().getRequestDispatcher("/Login").forward(request, response);
		} else {
			request.getSession().setAttribute("messageupdate", "");
			request.getServletContext().getRequestDispatcher("/WEB-INF/modify_profile.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String choice = (String) request.getParameter("accountaction");
		User userDataS = (User) request.getSession().getAttribute("loggedInUser");
		int no_utilisateur = userDataS.getNo_utilisateur();
		request.getSession().setAttribute("messageupdate", "");

		if (choice == null) {

			request.getServletContext().getRequestDispatcher("/WEB-INF/modify_profile.jsp").forward(request, response);

		} else if (choice.equals("Modify")) {

			String pseudo = request.getParameter("pseudo");
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String email = request.getParameter("email");
			String telephone = request.getParameter("telephone");
			String rue = request.getParameter("rue");
			String code_postal = request.getParameter("code_postal");
			String ville = request.getParameter("ville");
			String mot_de_passe = request.getParameter("mot_de_passe");
			int credit = userDataS.getCredit();
			int administrateur = userDataS.getAdministrateur();
			String status = userDataS.getStatus();

			userDataS = new User(no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville,
					mot_de_passe, credit, administrateur, status);

			UserManager.update(no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville,
					mot_de_passe);
			request.getSession().setAttribute("loggedInUser", userDataS);
			request.getSession().setAttribute("messageupdate", "Profile updated successfully");
			request.getServletContext().getRequestDispatcher("/ProfileDetailsUser").forward(request, response);

		} else if (choice.equals("DeleteAccount")) {

			boolean testUser = UserManager.checkUser(no_utilisateur);

			if (testUser) {
				
				request.getSession().setAttribute("messageupdate", "User can not be deleted due to currently active bids");
				request.getServletContext().getRequestDispatcher("/ProfileDetailsUser").forward(request, response);
				
			} else {

				ProductManager productManager = new ProductManager();
				// part 1 refund money
				// select all current auctions, refund top bidder and delete auction
				List<ProductSold> listAuctions = productManager.currentAuctionByUserAll(no_utilisateur);
				List<Integer> articleList = listAuctions.stream().map(ProductSold::getNo_article)
						.collect(Collectors.toList());
				articleList.forEach(System.out::println);
				for (Integer test : articleList) {
					int bestOffer = productManager.selectBestAuction(test).getMontant_enchere();
					int topBuyer = productManager.UserWithBestAuction(test, bestOffer).getNo_utilisateur();
					int userCredit = productManager.selectUser(topBuyer).getCredit();
					productManager.setCredit(bestOffer + userCredit, topBuyer);
					productManager.deleteSold(test);
				}

				// part 2 delete auctions
				// collect all past and future sales based on a user id
				listAuctions = productManager.finishedAuctionByUserAll(no_utilisateur);
				listAuctions.addAll(productManager.futurAuctionByUserAll(no_utilisateur));

				// retrieve just no_article from the list and check the result
				articleList = listAuctions.stream().map(ProductSold::getNo_article).collect(Collectors.toList());
				articleList.forEach(System.out::println);

				// delete all the sales
				for (Integer test : articleList) {
					productManager.deleteSold(test);
				}
				// delete user and disconnect
				UserManager.delete(no_utilisateur);
				request.getSession().removeAttribute("loggedInUser");
				request.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			}
		}
	}

}
