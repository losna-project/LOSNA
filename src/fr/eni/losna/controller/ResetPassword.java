package fr.eni.losna.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.losna.bll.UserManager;
import fr.eni.losna.bo.PasswordResetToken;

@WebServlet("/ResetPassword")
public class ResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PasswordResetToken myToken = (PasswordResetToken) request.getSession().getAttribute("myToken");
		System.out.println(myToken);

		if (myToken == null) {

			request.getSession().setAttribute("messagelogin", "Link incorrect, please check your email and try again");
			request.getServletContext().getRequestDispatcher("/Login").forward(request, response);

		} else {

			String token = myToken.getToken();
			LocalDateTime expiryDate = myToken.getExpiryDate();
			int user = myToken.getUser();
			LocalDateTime currentDate = LocalDateTime.now();

			if (myToken.getExpiryDate().isAfter(currentDate)) {

				request.getServletContext().getRequestDispatcher("/WEB-INF/reset_password.jsp").forward(request,
						response);
			} else {

				request.getSession().setAttribute("messagelogin", "Link explired. Request new password reset link");
				request.getServletContext().getRequestDispatcher("/Login").forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PasswordResetToken myToken = (PasswordResetToken) request.getSession().getAttribute("myToken");
		int no_utilisateur = myToken.getUser();
		String password = request.getParameter("pw");

		UserManager.resetPw(no_utilisateur, password);
		HttpSession session = request.getSession(false);
		session.invalidate();
		request.getSession().setAttribute("messagelogin", "Password was successfully reset");
		request.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

}
