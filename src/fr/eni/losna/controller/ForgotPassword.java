package fr.eni.losna.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.losna.bll.UserManager;
import fr.eni.losna.bo.PasswordResetToken;
import fr.eni.losna.utility.Mailer;

@WebServlet("/ForgotPassword")
public class ForgotPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setAttribute("messagelogin", "");
		request.getServletContext().getRequestDispatcher("/WEB-INF/forgot_password.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String email = (String) request.getParameter("email");
		int user_no = UserManager.userCheck(email).getNo_utilisateur();
		String url = "http://localhost:8080/LOSNA/ResetPassword";
		String sender = "?????@gmail.com";
		String pwsender = "?????";
		String subject = "LOSNA Password Reset";
		String message = ""; 
		LocalDateTime expiryDate = LocalDateTime.now();
		expiryDate = expiryDate.plusHours(24);
		
		if (user_no == 0) {
			HttpSession session = request.getSession(false);
			session.invalidate();
			request.setAttribute("messagelogin", "Error: Email not registered");
			request.getServletContext().getRequestDispatcher("/WEB-INF/forgot_password.jsp").forward(request, response);
		} else {	
			
			String token = UUID.randomUUID().toString();
			PasswordResetToken myToken = UserManager.createPasswordResetTokenForUser(user_no, token, expiryDate);
			request.getSession().setAttribute("myToken", myToken);
			url = url + "?token=" + token;
			message = "Here is your password reset link: " + url + "."; 
			
			Mailer.send(sender, pwsender, email, subject, message);
			request.getSession().setAttribute("messagelogin", "Check your email for password reset link. The link will expire in 24 hours.");
			request.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		}
	}
}