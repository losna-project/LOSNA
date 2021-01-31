package fr.eni.losna.bo;

import java.time.LocalDateTime;

import fr.eni.losna.dal.DAOFactory;

public class Auction {

	private int no_enchere;
	private LocalDateTime date_enchere;
	private int montant_enchere;
	private int no_article;
	private int no_utilisateur;

	public int getNo_enchere() {
		return no_enchere;
	}

	public void setNo_enchere(int no_enchere) {
		this.no_enchere = no_enchere;
	}

	public LocalDateTime getDate_enchere() {
		return date_enchere;
	}

	public void setDate_enchere(LocalDateTime date_enchere) {
		this.date_enchere = date_enchere;
	}

	public int getMontant_enchere() {
		return montant_enchere;
	}

	public void setMontant_enchere(int montant_enchere) {
		this.montant_enchere = montant_enchere;
	}

	public int getNo_article() {
		return no_article;
	}

	public void setNo_article(int no_article) {
		this.no_article = no_article;
	}

	public int getNo_utilisateur() {
		return no_utilisateur;
	}

	public void setNo_utilisateur(int no_utilisateur) {
		this.no_utilisateur = no_utilisateur;
	}

	public Auction(int no_enchere, LocalDateTime date_enchere, int montant_enchere, int no_article,
			int no_utilisateur) {
		super();
		this.no_enchere = no_enchere;
		this.date_enchere = date_enchere;
		this.montant_enchere = montant_enchere;
		this.no_article = no_article;
		this.no_utilisateur = no_utilisateur;
	}

	public Auction() {
	}

	public ProductSold selectByNoProduct(int NoProduct) {
		return DAOFactory.getLosnaDAO().selectByNoProduct(NoProduct);
	}

	public String selectUserByProduct(int NoProduct) {
		User user = DAOFactory.getLosnaDAO().selectUserByProduct(NoProduct);
		return user.getPseudo();
	}

	public int selectBestAuction(int NoProduct) {
		Auction auction = DAOFactory.getLosnaDAO().selectBestAuction(NoProduct);
		int bestAuction = auction.getMontant_enchere();
		return bestAuction;
	}

	public Auction(int montant_enchere, int no_article, int no_utilisateur) {
		super();
		this.montant_enchere = montant_enchere;
		this.no_article = no_article;
		this.no_utilisateur = no_utilisateur;
	}

	@Override
	public String toString() {
		return "Auction [no_enchere=" + no_enchere + ", date_enchere=" + date_enchere + ", montant_enchere="
				+ montant_enchere + ", no_article=" + no_article + ", no_utilisateur=" + no_utilisateur + "]";
	}
	
	public User selectUser(int no_utilisateur) {
        return DAOFactory.getLosnaDAO().selectUser(no_utilisateur);
}
}
