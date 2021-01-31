package fr.eni.losna.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BuyCredits")
public class BuyCredits extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("loggedInUser") == null) {
			request.getServletContext().getRequestDispatcher("/Login").forward(request, response);
		} else {
			
		int cost = 0;
		request.setAttribute("cost", cost);

		request.getServletContext().getRequestDispatcher("/WEB-INF/buy_credits.jsp").forward(request, response);
	}
}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int credits = Integer.parseInt(request.getParameter("credits"));
		int cost;
		cost = credits*10;
		request.getSession().setAttribute("credits", credits);
		request.getSession().setAttribute("cost", cost);
		request.getServletContext().getRequestDispatcher("/WEB-INF/buy_credits.jsp").forward(request, response);
	}

}
