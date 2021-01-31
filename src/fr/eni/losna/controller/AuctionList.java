package fr.eni.losna.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.microsoft.sqlserver.jdbc.StringUtils;

import fr.eni.losna.bll.ProductManager;
import fr.eni.losna.bo.Auction;
import fr.eni.losna.bo.Category;
import fr.eni.losna.bo.Pagination;
import fr.eni.losna.bo.ProductSold;
import fr.eni.losna.bo.User;

@WebServlet("/AuctionList")
public class AuctionList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductManager productManager = new ProductManager();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LocalDate today = LocalDate.now();
		request.setAttribute("today", today);

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loggedInUser") == null) {
			request.getServletContext().getRequestDispatcher("/Login").forward(request, response);
		} else {
			doPost(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LocalDate today = LocalDate.now();
		request.setAttribute("today", today);
		
		List<Category> listCatagory = productManager.categories();
		request.setAttribute("listCategory", listCatagory);

		String selectbyname = request.getParameter("selectbyname");
		System.out.println(selectbyname);
		String select = request.getParameter("select");
		System.out.println(select);
		String selectcheck = request.getParameter("selectcheck");
		System.out.println(selectcheck);
		request.setAttribute("enchere", 0);

		// Récupère le numéro de l'utilisateur courant
		User user = (User) request.getSession().getAttribute("loggedInUser");
		int noUser = user.getNo_utilisateur();
		System.out.println(noUser);
		// check if the user is banned
		String status = user.getStatus();
		request.setAttribute("status", status);
		
		int credits = productManager.selectUser(noUser).getCredit();
		request.setAttribute("credits", credits);

		String i = request.getParameter("i");
		System.out.println("pagination" + i);

		if (StringUtils.isEmpty(selectbyname) && StringUtils.isEmpty(select)) {

			Pagination total = new Pagination(1, 10000);
			List<Integer> pages = productManager.nbPages(productManager.selectAllProducts(total));
			request.setAttribute("nbPages", pages);

			if (request.getParameter("page") != null) {
				Pagination pagination = productManager.pagination(request.getParameter("page"));

				List<ProductSold> selectAllProducts = productManager.selectAllProducts(pagination);
				request.setAttribute("selectItems", selectAllProducts);
			} else {
				Pagination pagination = new Pagination(1, 7);
				List<ProductSold> selectAllProducts = productManager.selectAllProducts(pagination);
				request.setAttribute("selectItems", selectAllProducts);
			}

		} else if (StringUtils.isEmpty(selectbyname) && select.equals("Toutes")) {

			if (selectcheck != null) {

				switch (selectcheck) {
				// afficher toutes les enchères en cours
				case "selectItems":
					Pagination total = new Pagination(1, 10000);
					List<Integer> pages = productManager.nbPages(productManager.selectAllProducts(total));
					request.setAttribute("nbPages", pages);

					if (request.getParameter("page") != null) {
						Pagination pagination = productManager.pagination(request.getParameter("page"));

						List<ProductSold> selectAllProducts = productManager.selectAllProducts(pagination);
						request.setAttribute("selectItems", selectAllProducts);
					} else {
						Pagination pagination = new Pagination(1, 7);
						List<ProductSold> selectAllProducts = productManager.selectAllProducts(pagination);
						request.setAttribute("selectItems", selectAllProducts);
					}
					break;
				// afficher les enchères de l'utilisateur
				case "userAuctions":
					request.setAttribute("enchere", 1);
					Pagination total1 = new Pagination(1, 10000);
					List<Integer> pages1 = productManager.nbPages(productManager.userAuctions(noUser, total1));
					request.setAttribute("nbPages", pages1);

					if (request.getParameter("page") != null) {
						Pagination pagination = productManager.pagination(request.getParameter("page"));

						List<Auction> userAuctions = productManager.userAuctions(noUser, pagination);
						request.setAttribute("selectItems", userAuctions);
					} else {
						Pagination pagination = new Pagination(1, 7);
						List<Auction> userAuctions = productManager.userAuctions(noUser, pagination);
						request.setAttribute("selectItems", userAuctions);
					}
					break;
				// afficher les enchères remportées de l'utilisateur
				case "auctionWon":
					request.setAttribute("enchere", 2);
					Pagination total2 = new Pagination(1, 10000);
					List<Integer> pages2 = productManager.nbPages(productManager.userAuctionsFinished(noUser, total2));
					request.setAttribute("nbPages", pages2);

					if (request.getParameter("page") != null) {
						Pagination pagination = productManager.pagination(request.getParameter("page"));

						List<Auction> userAuctionsFinished = productManager.userAuctionsFinished(noUser, pagination);
						request.setAttribute("selectItems", userAuctionsFinished);
					} else {
						Pagination pagination = new Pagination(1, 7);
						List<Auction> userAuctionsFinished = productManager.userAuctionsFinished(noUser, pagination);
						request.setAttribute("selectItems", userAuctionsFinished);
					}
					break;
				// afficher les ventes en cours de l'utilisateur
				case "currentAuction":
					Pagination total3 = new Pagination(1, 10000);
					List<Integer> pages3 = productManager.nbPages(productManager.currentAuctionByUser(noUser, total3));
					request.setAttribute("nbPages", pages3);

					if (request.getParameter("page") != null) {
						Pagination pagination = productManager.pagination(request.getParameter("page"));

						List<ProductSold> currentAuctionByUser = productManager.currentAuctionByUser(noUser,
								pagination);
						request.setAttribute("selectItems", currentAuctionByUser);
					} else {
						Pagination pagination = new Pagination(1, 7);
						List<ProductSold> currentAuctionByUser = productManager.currentAuctionByUser(noUser,
								pagination);
						request.setAttribute("selectItems", currentAuctionByUser);
					}
					break;
				// afficher les ventes de l'utilisateur non débutées
				case "futurAuction":
					request.setAttribute("voirVente", 1);
					Pagination total4 = new Pagination(1, 10000);
					List<Integer> pages4 = productManager.nbPages(productManager.futurAuctionByUser(noUser, total4));
					request.setAttribute("nbPages", pages4);

					if (request.getParameter("page") != null) {
						Pagination pagination = productManager.pagination(request.getParameter("page"));

						List<ProductSold> futurAuctionByUser = productManager.futurAuctionByUser(noUser, pagination);
						request.setAttribute("selectItems", futurAuctionByUser);
					} else {
						Pagination pagination = new Pagination(1, 7);
						List<ProductSold> futurAuctionByUser = productManager.futurAuctionByUser(noUser, pagination);
						request.setAttribute("selectItems", futurAuctionByUser);
					}
					break;
				// afficher les ventes de l'utilisateur terminées
				case "finishedAuction":
					Pagination total5 = new Pagination(1, 10000);
					List<Integer> pages5 = productManager.nbPages(productManager.finishedAuctionByUser(noUser, total5));
					request.setAttribute("nbPages", pages5);

					if (request.getParameter("page") != null) {
						Pagination pagination = productManager.pagination(request.getParameter("page"));

						List<ProductSold> finishedAuctionByUser = productManager.finishedAuctionByUser(noUser,
								pagination);
						request.setAttribute("selectItems", finishedAuctionByUser);
					} else {
						Pagination pagination = new Pagination(1, 7);
						List<ProductSold> finishedAuctionByUser = productManager.finishedAuctionByUser(noUser,
								pagination);
						request.setAttribute("selectItems", finishedAuctionByUser);
					}

					break;
				default:
					Pagination total6 = new Pagination(1, 10000);
					List<Integer> pages6 = productManager.nbPages(productManager.selectAllProducts(total6));
					request.setAttribute("nbPages", pages6);

					if (request.getParameter("page") != null) {
						Pagination pagination = productManager.pagination(request.getParameter("page"));

						List<ProductSold> selectAllProducts = productManager.selectAllProducts(pagination);
						request.setAttribute("selectItems", selectAllProducts);
					} else {
						Pagination pagination = new Pagination(1, 7);
						List<ProductSold> selectAllProducts = productManager.selectAllProducts(pagination);
						request.setAttribute("selectItems", selectAllProducts);
					}
					break;
				}

			} else {

				Pagination total6 = new Pagination(1, 10000);
				List<Integer> pages6 = productManager.nbPages(productManager.selectAllProducts(total6));
				request.setAttribute("nbPages", pages6);

				if (request.getParameter("page") != null) {
					Pagination pagination = productManager.pagination(request.getParameter("page"));

					List<ProductSold> selectAllProducts = productManager.selectAllProducts(pagination);
					request.setAttribute("selectItems", selectAllProducts);
				} else {
					Pagination pagination = new Pagination(1, 7);
					List<ProductSold> selectAllProducts = productManager.selectAllProducts(pagination);
					request.setAttribute("selectItems", selectAllProducts);
				}

			}

		} else if (StringUtils.isEmpty(selectbyname) && !select.equals("Toutes")) {

			Pagination total = new Pagination(1, 10000);
			List<Integer> pages = productManager.nbPages(productManager.selectByCategory(select, total));
			request.setAttribute("nbPages", pages);

			if (request.getParameter("page") != null) {
				Pagination pagination = productManager.pagination(request.getParameter("page"));
				List<ProductSold> selectByCategory = productManager.selectByCategory(select, pagination);
				request.setAttribute("selectItems", selectByCategory);
			} else {
				Pagination pagination = new Pagination(1, 7);
				List<ProductSold> selectByCategory = productManager.selectByCategory(select, pagination);
				request.setAttribute("selectItems", selectByCategory);
			}
		} else {

			// Afficher la liste des produits par nom
			Pagination total = new Pagination(1, 10000);
			List<Integer> pages = productManager.nbPages(productManager.selectByName(selectbyname, total));
			request.setAttribute("nbPages", pages);

			if (request.getParameter("page") != null) {
				Pagination pagination = productManager.pagination(request.getParameter("page"));
				List<ProductSold> selectByName = productManager.selectByName(selectbyname, pagination);
				request.setAttribute("selectItems", selectByName);
			} else {
				Pagination pagination = new Pagination(1, 7);
				List<ProductSold> selectByName = productManager.selectByName(selectbyname, pagination);
				request.setAttribute("selectItems", selectByName);
			}

		}

		request.getServletContext().getRequestDispatcher("/WEB-INF/auction_list.jsp").forward(request, response);
	}
}