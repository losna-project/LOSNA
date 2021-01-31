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

@WebServlet("/AdminTasks")
public class AdminTasks extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("loggedInUser") == null) {

			request.getServletContext().getRequestDispatcher("/Login").forward(request, response);
		} else {

			int no_utilisateur = Integer.parseInt(request.getParameter("user_id"));
			request.getSession().setAttribute("user_manager", no_utilisateur);
			request.getSession().setAttribute("messageadmin", "");
			request.getServletContext().getRequestDispatcher("/WEB-INF/admin_tasks.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String choice = (String) request.getParameter("accountaction");
		int no_utilisateur = (Integer) request.getSession().getAttribute("user_manager");
		String status = "ban";

		if (choice.equals("DeleteAccount")) {

			boolean testUser = UserManager.checkUser(no_utilisateur);

			if (testUser) {

				request.getSession().setAttribute("messageadmin",
						"User can not be deleted due to currently active bids");
				request.getServletContext().getRequestDispatcher("/WEB-INF/admin_tasks.jsp").forward(request, response);

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

				// delete user
				UserManager.delete(no_utilisateur);
				request.getSession().setAttribute("messageadmin", "User was deleted from the database");
				request.getServletContext().getRequestDispatcher("/WEB-INF/admin_tasks.jsp").forward(request, response);

			}

		} else if (choice.equals("Deactivate")) {

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

			// change user status to deactivated, they can still log in but can't post sales
			// or bid

			UserManager.setStatus(status, no_utilisateur);
			request.getSession().setAttribute("messageadmin", "User was successfully banned!");
			request.getServletContext().getRequestDispatcher("/WEB-INF/admin_tasks.jsp").forward(request, response);
		}
	}
}