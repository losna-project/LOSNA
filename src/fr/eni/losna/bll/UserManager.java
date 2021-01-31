package fr.eni.losna.bll;

import java.time.LocalDateTime;
import java.util.List;

import fr.eni.losna.bo.Category;
import fr.eni.losna.bo.PasswordResetToken;
import fr.eni.losna.bo.User;
import fr.eni.losna.dal.DAOFactory;

public class UserManager {

	private static UserManager instance = null;

	public static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}
	
	public static List<User> users() {
		return DAOFactory.getLosnaDAO().users();
	}
	
	public static boolean validateCreditCard(String cardnumber) {

		boolean test;
		
		cardnumber = cardnumber.replaceAll(" ", "");
		int[] ints = new int[cardnumber.length()];
		for (int i = 0; i < cardnumber.length(); i++) {
			ints[i] = Integer.parseInt(cardnumber.substring(i, i + 1));
		}
		for (int i = ints.length - 2; i >= 0; i = i - 2) {
			int j = ints[i];
			j = j * 2;
			if (j > 9) {
				j = j % 10 + 1;
			}
			ints[i] = j;
		}
		int sum = 0;
		for (int i = 0; i < ints.length; i++) {
			sum += ints[i];
		}
		if (sum % 10 == 0) {
			test = true;
		} else {
			test = false;
		}
		return test;
	}
	
	public static User userEmail(int no_utilisateur) {
		return DAOFactory.getLosnaDAO().userEmail(no_utilisateur);
	}
	
	public static boolean checkUser(int no_utilisateur) {
		return DAOFactory.getLosnaDAO().checkUser(no_utilisateur);
	}

	public static PasswordResetToken createPasswordResetTokenForUser(int user, String token, LocalDateTime expiryDate) {
		PasswordResetToken myToken = new PasswordResetToken(token, user, expiryDate);
		return myToken;
	}

	public static User register(String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String code_postal, String ville, String mot_de_passe) {
		User userData = new User(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe);
		return DAOFactory.getLosnaDAO().register(userData);
	}

	public static User connect(String pseudo, String mot_de_passe) {
		return DAOFactory.getLosnaDAO().connect(pseudo, mot_de_passe);
	}

	public static void update(int no_utilisateur, String pseudo, String nom, String prenom, String email,
			String telephone, String rue, String code_postal, String ville, String mot_de_passe) {
		User userData = new User(no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville,
				mot_de_passe);
		DAOFactory.getLosnaDAO().update(userData);
	}

	public static void delete(int no_utilisateur) {
		DAOFactory.getLosnaDAO().delete(no_utilisateur);
	}

	public static void setStatus(String status, int no_utilisateur) {
		DAOFactory.getLosnaDAO().setStatus(status, no_utilisateur);
	}

	public static User userCheck(String email) {
		return DAOFactory.getLosnaDAO().userCheck(email);
	}

	public static void resetPw(int no_utilisateur, String email) {
		DAOFactory.getLosnaDAO().resetPw(no_utilisateur, email);
	}

}
