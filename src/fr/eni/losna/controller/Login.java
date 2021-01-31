package fr.eni.losna.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.losna.bll.UserManager;
import fr.eni.losna.bo.User;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.getSession().setAttribute("messagelogin", "");
		
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];

				if (c.getName().equals("userid")) {
					String userId = c.getValue();
					request.getSession().setAttribute("username", userId);
				}

				if (c.getName().equals("userpw")) {
					String userPw = c.getValue();
					request.getSession().setAttribute("password", userPw);
				}
			}
		}

		request.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User userDataS = new User();
		request.getSession().setAttribute("messagelogin", "");
		// get checkbox value
		String check = request.getParameter("checkbox");

		String pseudo = request.getParameter("pseudo");
		//System.out.println(pseudo);
		String mot_de_passe = request.getParameter("mot_de_passe");
		//System.out.println(mot_de_passe);
		userDataS = UserManager.connect(pseudo, mot_de_passe);

		if (userDataS != null) {

			if (check != null ) {
				Cookie c = new Cookie("userid", pseudo);
				c.setMaxAge(60*60*24);
				response.addCookie(c);
				c = new Cookie("userpw", mot_de_passe);
				c.setMaxAge(60*60*24); 
				response.addCookie(c);
			} else {
			}
			
			System.out.println(userDataS.getStatus());
			request.getSession().setAttribute("loggedInUser", userDataS);
			request.getServletContext().getRequestDispatcher("/AuctionList").forward(request, response);
		} else {
			request.getSession().setAttribute("messagelogin", "Username or password do not match");
			request.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		}
	}
}
