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
import fr.eni.losna.bo.Category;
import fr.eni.losna.bo.Pagination;
import fr.eni.losna.bo.ProductSold;
import fr.eni.losna.utility.BatchRunner;

@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductManager productManager = new ProductManager();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
// ------------------------------------------------------------------------------------------------------------------------------------//
// The batch process will check for all auctions that are completed on the day, update sale price and send confirmation email to buyers//
// It will then repeat the process every 24 hours for months (if you leave the server running :)//

		HttpSession session = request.getSession(false);
		if (session == null) {
			BatchRunner.dailyBatch();
		}

// ------------------------------------------------------------------------------------------------------------------------------------//	
// -----------------------------------------------------------------------------------------------------------------------------------//	
		LocalDate today = LocalDate.now();
		request.setAttribute("today", today);

		List<Category> listCatagory = productManager.categories();
		request.setAttribute("listCategory", listCatagory);

		// ---------------------------------------------------------------------------
		// Affichage des 10000 premières lignes de la BDD
		Pagination total = new Pagination(1, 10000);
		List<Integer> pages = productManager.nbPages(productManager.selectAllProducts(total));
		request.setAttribute("nbPages", pages);

		// Affiche pour la méthode get, les 6 premiers article par défaut
		Pagination pagination = new Pagination(1, 7);
		List<ProductSold> selectAllProducts = productManager.selectAllProducts(pagination);
		request.setAttribute("selectItems", selectAllProducts);

		request.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// request.setAttribute("nbPages", pages);/////////////

		LocalDate today = LocalDate.now();
		request.setAttribute("today", today);

		List<Category> listCatagory = productManager.categories();
		request.setAttribute("listCategory", listCatagory);

		String selectbyname = request.getParameter("selectbyname");
		System.out.println(selectbyname);
		String select = request.getParameter("select");
		System.out.println(select);

		if (StringUtils.isEmpty(selectbyname) && select.equals("Toutes")) {

			// compter le nombre de pages nécessaires à l'affichage de la liste sélectionnée
			Pagination total = new Pagination(1, 10000);
			List<Integer> pages = productManager.nbPages(productManager.selectAllProducts(total));
			System.out.println(pages);
			request.setAttribute("nbPages", pages);

			// affiche les pages suivantes
			if (request.getParameter("page") != null) {
				Pagination pagination = productManager.pagination(request.getParameter("page"));

				List<ProductSold> selectAllProducts = productManager.selectAllProducts(pagination);
				request.setAttribute("selectItems", selectAllProducts);
			} else {
				Pagination pagination = new Pagination(1, 7);
				List<ProductSold> selectAllProducts = productManager.selectAllProducts(pagination);
				request.setAttribute("selectItems", selectAllProducts);
			}

			System.out.println("Method1");

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

			System.out.println("Method2");

		} else {
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
			System.out.println("Method3");
		}

		request.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
	}

}