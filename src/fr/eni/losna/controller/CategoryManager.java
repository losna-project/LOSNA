package fr.eni.losna.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.losna.bll.ProductManager;
import fr.eni.losna.bll.UserManager;
import fr.eni.losna.bo.Category;
import fr.eni.losna.bo.ProductSold;
import fr.eni.losna.bo.User;

@WebServlet("/CategoryManager")
public class CategoryManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductManager productManager = new ProductManager();

		List<Category> listCatagory = productManager.categories();
		request.setAttribute("listCategory", listCatagory);

		List<User> listUser = UserManager.users();
		request.setAttribute("listUser", listUser);

		request.getServletContext().getRequestDispatcher("/WEB-INF/category_manager.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductManager productManager = new ProductManager();
		String choice = (String) request.getParameter("modcat");

		if (choice.equals("DeleteCategory")) {

			int delCat = Integer.parseInt(request.getParameter("select"));
			boolean test = productManager.selectCat(delCat);

			if (test) {

				request.setAttribute("messageadmin", "The category can not be removed as it is curently in use");

			} else {

				productManager.deleteCat(delCat);
				request.setAttribute("messageadmin", "The category was removed");
			}

		} else if (choice.equals("AddCategory")) {

			String addCat = (String) request.getParameter("addcat");
			productManager.addCat(addCat);
			request.setAttribute("messageadmin", "The category was added successfully");

		} else if (choice.equals("BanUser")) {

			int no_utilisateur = Integer.parseInt(request.getParameter("user"));
			String status = "ban";
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

		} else if (choice.equals("DeleteUser")) {
			
			int no_utilisateur = Integer.parseInt(request.getParameter("user"));
			boolean testUser = UserManager.checkUser(no_utilisateur);

			if (testUser) {

				request.getSession().setAttribute("messageadmin",
						"User can not be deleted due to currently active bids");

			} else {

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
			}

		}

		doGet(request, response);
	}

}
