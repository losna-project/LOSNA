package fr.eni.losna.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.losna.bll.ProductManager;
import fr.eni.losna.bo.Auction;
import fr.eni.losna.bo.User;

/**
 * Servlet implementation class BidderList
 */
@WebServlet("/BidderList")
public class BidderList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ProductManager productManager = new ProductManager();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Récupère le numéro de l'utilisateur courant
		User user = (User) request.getSession().getAttribute("loggedInUser");
		int noUser = user.getNo_utilisateur();
		System.out.println(noUser);
		
		List<Auction>listeEncherisseur = productManager.listeEncherisseur(noUser);
		request.setAttribute("bidderList", listeEncherisseur);
		System.out.println(listeEncherisseur);
		
		request.getServletContext().getRequestDispatcher("/WEB-INF/bidder_list.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
