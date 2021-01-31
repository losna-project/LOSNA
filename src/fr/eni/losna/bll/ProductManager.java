package fr.eni.losna.bll;

import java.util.ArrayList;
import java.util.List;

import fr.eni.losna.bo.Auction;
import fr.eni.losna.bo.Category;
import fr.eni.losna.bo.DeliveryAddress;
import fr.eni.losna.bo.Pagination;
import fr.eni.losna.bo.ProductSold;
import fr.eni.losna.bo.User;
import fr.eni.losna.dal.DAOFactory;

public class ProductManager {
	
	public List<ProductSold> auctionByDate() {
		return DAOFactory.getLosnaDAO().auctionByDate();
	}
	
	public boolean selectCat(int category) {
		return DAOFactory.getLosnaDAO().selectCat(category);
	}
	
	public void deleteCat (int category) {
		DAOFactory.getLosnaDAO().deleteCat(category);
	}
	
	public void addCat (String category) {
		DAOFactory.getLosnaDAO().addCat(category);
	}
	
	public List<Category> categories() {
		return DAOFactory.getLosnaDAO().categories();
	}

	public void upload(int no_utilisateur, String fileName) {
		DAOFactory.getLosnaDAO().upload(no_utilisateur, fileName);
	}

	// Maxime's code
	public List<ProductSold> selectByNameCategory(String nomCategory, String name) {
		return DAOFactory.getLosnaDAO().selectByNameCategory(nomCategory, name);
	}

	public ProductSold selectByNoProduct(int NoProduct) {
		return DAOFactory.getLosnaDAO().selectByNoProduct(NoProduct);
	}

	public Category selectCategoryByNoProduct(int NoProduct) {
		return DAOFactory.getLosnaDAO().selectCategoryByNoProduct(NoProduct);
	}

	public Auction selectBestAuction(int NoProduct) {
		return DAOFactory.getLosnaDAO().selectBestAuction(NoProduct);
	}

	public DeliveryAddress selectByNoArticle(int NoProduct) {
		return DAOFactory.getLosnaDAO().selectByNoArticle(NoProduct);
	}

	public User selectUserByProduct(int NoProduct) {
		return DAOFactory.getLosnaDAO().selectUserByProduct(NoProduct);
	}

	public void encherir(int prix, int no_user, int no_article) {
		DAOFactory.getLosnaDAO().encherir(prix, no_user, no_article);
	}

	public void setCredit(int prix, int no_user) {
		DAOFactory.getLosnaDAO().setCredit(prix, no_user);
	}

	public Auction enchereUtilisateurByNoArticle(int no_user, int no_article) {
		return DAOFactory.getLosnaDAO().enchereUtilisateurByNoArticle(no_user, no_article);
	}

	public void addNewAuction(Auction auction) {
		DAOFactory.getLosnaDAO().addNewAuction(auction);
	}

	public User selectUser(int no_utilisateur) {
		return DAOFactory.getLosnaDAO().selectUser(no_utilisateur);
	}

	public Auction UserWithBestAuction(int no_article, int Max_enchere) {
		return DAOFactory.getLosnaDAO().UserWithBestAuction(no_article, Max_enchere);
	}

	public void addArticle(ProductSold product) {
		DAOFactory.getLosnaDAO().addArticle(product);
	}

	public void addNewDelivery(DeliveryAddress deliveryAddress) {
		DAOFactory.getLosnaDAO().addNewDelivery(deliveryAddress);
	}

	public void deleteSold(int no_article) {
		DAOFactory.getLosnaDAO().deleteAuction(no_article);
		DAOFactory.getLosnaDAO().deleteDelivery(no_article);
		DAOFactory.getLosnaDAO().deleteArticle(no_article);
	}

	public void updateArticle(ProductSold product, int no_article) {
		DAOFactory.getLosnaDAO().updateArticle(product, no_article);
	}

	public void updateDelivery(DeliveryAddress deliveryAddress, int no_article) {
		DAOFactory.getLosnaDAO().updateDelivery(deliveryAddress, no_article);
	}

	// Calcule l'interval de ligne à rechercher en BDD en fonction de la page
	// demandée
	public Pagination pagination(String numeroDePage) {
		int noPage = Integer.parseInt(numeroDePage);
		int compteur = 0;
		int debut = -5;
		int fin = 1;
		while (compteur < noPage) {
			debut = debut + 6;
			fin = fin + 6;
			compteur++;
		}
		Pagination pagination = new Pagination(debut, fin);
		return pagination;
	}

	// Calcule le nombre de page à afficher
	public <T> List<Integer> nbPages(List<T> listeArticle) {

		int i = 0;
		int j = 0;
		List<Integer> pages = new ArrayList<Integer>();
		pages.add(1);
		while (i < listeArticle.size()) {
			i++;
			if (i == j + 7) {
				pages.add(1);
				j = j + 6;
			}
		}
		return pages;
	}

	public List<ProductSold> selectAllProducts(Pagination pagination) {
		return DAOFactory.getLosnaDAO().selectAllProducts(pagination);
	}

	public List<ProductSold> selectByName(String name, Pagination pagination) {
		return DAOFactory.getLosnaDAO().selectByName(name, pagination);
	}

	public List<ProductSold> selectByCategory(String nomCategory, Pagination pagination) {
		return DAOFactory.getLosnaDAO().selectByCategory(nomCategory, pagination);
	}

	public List<Auction> userAuctions(int noUser, Pagination pagination) {
		return DAOFactory.getLosnaDAO().userAuctions(noUser, pagination);
	}

	public List<Auction> userAuctionsFinished(int noUser, Pagination pagination) {
		return DAOFactory.getLosnaDAO().userAuctionsFinished(noUser, pagination);
	}

	public List<ProductSold> currentAuctionByUserAll(int noUser) {
		return DAOFactory.getLosnaDAO().currentAuctionByUserAll(noUser);
	}

	public List<ProductSold> futurAuctionByUserAll(int noUser) {
		return DAOFactory.getLosnaDAO().futurAuctionByUserAll(noUser);
	}

	public List<ProductSold> finishedAuctionByUserAll(int noUser) {
		return DAOFactory.getLosnaDAO().finishedAuctionByUserAll(noUser);
	}

	public List<ProductSold> currentAuctionByUser(int noUser, Pagination pagination) {
		return DAOFactory.getLosnaDAO().currentAuctionByUser(noUser, pagination);
	}

	public List<ProductSold> futurAuctionByUser(int noUser, Pagination pagination) {
		return DAOFactory.getLosnaDAO().futurAuctionByUser(noUser, pagination);
	}

	public List<ProductSold> finishedAuctionByUser(int noUser, Pagination pagination) {
		return DAOFactory.getLosnaDAO().finishedAuctionByUser(noUser, pagination);
	}

	public void updatePrixVente(int prix, int no_article) {
		DAOFactory.getLosnaDAO().updatePrixVente(prix, no_article);
	}

	public List<Auction> listeEncherisseur(int no_user) {
		return DAOFactory.getLosnaDAO().listeEncherisseur(no_user);
	}
}
