package fr.eni.losna.dal;

import java.util.List;

import fr.eni.losna.bo.Auction;
import fr.eni.losna.bo.Category;
import fr.eni.losna.bo.DeliveryAddress;
import fr.eni.losna.bo.Pagination;
import fr.eni.losna.bo.ProductSold;
import fr.eni.losna.bo.User;

public interface LosnaDAO {

	public List<User> users();

	public User userEmail(int no_utilisateur);

	public List<ProductSold> auctionByDate();

	public boolean checkUser(int no_utilisateur);

	public boolean selectCat(int category);

	public void deleteCat(int category);

	public void addCat(String category);

	public List<Category> categories();

	public User register(User userData);

	public User connect(String pseudo, String mot_de_passe);

	public void update(User userData);

	public void delete(int no_utilisateur);

	public void setStatus(String administrateur, int no_utilisateur);

	public User userCheck(String email);

	public void resetPw(int no_utilisateur, String email);

	// image upload method
	public void upload(int no_utilisateur, String fileName);

	// Maxime's code
	public List<Auction> userAuctions(int noUser, Pagination pagination);

	public List<Auction> userAuctionsFinished(int noUser, Pagination pagination);

	public List<ProductSold> currentAuctionByUser(int noUser, Pagination pagination);

	public List<ProductSold> futurAuctionByUser(int noUser, Pagination pagination);

	public List<ProductSold> finishedAuctionByUser(int noUser, Pagination pagination);

	public List<ProductSold> currentAuctionByUserAll(int noUser);

	public List<ProductSold> futurAuctionByUserAll(int noUser);

	public List<ProductSold> finishedAuctionByUserAll(int noUser);

	public List<ProductSold> selectAllProducts(Pagination pagination);

	public List<ProductSold> selectByName(String name, Pagination pagination);

	public List<ProductSold> selectByCategory(String nomCategory, Pagination pagination);

	public List<ProductSold> selectByNameCategory(String nomCategory, String name);

	public ProductSold selectByNoProduct(int NoProduct);

	public Category selectCategoryByNoProduct(int NoProduct);

	public Auction selectBestAuction(int NoProduct);

	public DeliveryAddress selectByNoArticle(int NoProduct);

	public User selectUserByProduct(int NoProduct);

	public void encherir(int prix, int no_user, int no_article);

	public void setCredit(int prix, int no_user);

	public List<Auction> encheresUtilisateur(int no_user);

	public void addNewAuction(Auction auction);

	public User selectUser(int no_utilisateur);

	public void addArticle(ProductSold product);

	public void addNewDelivery(DeliveryAddress deliveryAddress);

	public Auction UserWithBestAuction(int no_article, int Max_enchere);

	public void deleteDelivery(int no_article);

	public void deleteArticle(int no_article);

	public void deleteAuction(int no_article);

	public void updateArticle(ProductSold product, int no_article);

	public void updateDelivery(DeliveryAddress deliveryAddress, int no_article);

	public Auction enchereUtilisateurByNoArticle(int no_user, int no_article);

	public void updatePrixVente(int prix, int no_article);

	public List<Auction> listeEncherisseur(int no_user);

}
