package fr.eni.losna.bo;

import java.time.LocalDateTime;

public class PasswordResetToken {

	private String token;
	private int user;
	private LocalDateTime expiryDate;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public PasswordResetToken(String token, int user, LocalDateTime expiryDate) {
		super();
		this.token = token;
		this.user = user;
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return "PassToken [token=" + token + ", user=" + user + ", expiryDate=" + expiryDate + "]";
	}
}
