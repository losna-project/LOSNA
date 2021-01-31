package fr.eni.losna.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.losna.bo.Auction;

import fr.eni.losna.bo.Category;
import fr.eni.losna.bo.DeliveryAddress;
import fr.eni.losna.bo.Pagination;
import fr.eni.losna.bo.ProductSold;
import fr.eni.losna.bo.User;

public class LosnaDAOImpl implements LosnaDAO {

	// register user information from the form in database
	// give default 100 points and set status to regular user (not administrator)

	@Override
	public List<User> users() {
		List<User> listUser = new ArrayList<>();

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM UTILISATEURS ORDER BY no_utilisateur");
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int no_utilisateur = rs.getInt("no_utilisateur");
				String pseudo = rs.getString("pseudo");
				User user = new User(no_utilisateur, pseudo);

				listUser.add(user);
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listUser;
	}

	@Override
	public User userEmail(int no_utilisateur) {

		User user = new User();

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con.prepareStatement("select * from utilisateurs where (no_utilisateur = ?)");
			st.setInt(1, no_utilisateur);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				user.setPseudo(rs.getString("pseudo"));
				user.setEmail(rs.getString("email"));
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	public List<ProductSold> auctionByDate() {

		List<ProductSold> listAuctions = new ArrayList<>();
		LocalDate batchDate = LocalDate.now();

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM ARTICLES_VENDUS WHERE date_fin_encheres = ? ");
			st.setObject(1, batchDate);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				ProductSold productSold = new ProductSold();
				productSold.setNo_article(rs.getInt("no_article"));
				productSold.setNom_article(rs.getString("nom_article"));
				productSold.setDescription(rs.getString("description"));
				productSold.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
				productSold.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
				productSold.setPrix_initial(rs.getInt("prix_initial"));
				productSold.setPrix_vente(rs.getInt("prix_vente"));
				productSold.setNo_utilisateur(rs.getInt("no_utilisateur"));
				productSold.setNo_categorie(rs.getInt("no_categorie"));
				productSold.setImages(rs.getString("images"));
				listAuctions.add(productSold);
			}
			rs.close();
			st.close();
		} catch (

		Exception e) {
			e.printStackTrace();
		}

		return listAuctions;
	}

	@Override
	public boolean checkUser(int no_utilisateur) {

		boolean test = true;

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con.prepareStatement(
					"SELECT * FROM ENCHERES e INNER JOIN ARTICLES_VENDUS a ON e.no_article = a.no_article WHERE e.no_utilisateur = ? AND date_fin_encheres > GETDATE() AND date_debut_encheres <= GETDATE()");
			st.setInt(1, no_utilisateur);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				test = true;
				System.out.println("User was found with auctions");
			} else {
				System.out.println("User has no auctions");
				test = false;
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return test;
	}

	@Override
	public boolean selectCat(int category) {

		boolean test = true;

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con.prepareStatement("select * from ARTICLES_VENDUS where no_categorie = ?");
			st.setInt(1, category);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				test = true;
			} else {
				test = false;
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return test;
	}

	@Override
	public void deleteCat(int category) {

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con.prepareStatement("delete from categories where no_categorie = ?");
			st.setInt(1, category);
			st.executeUpdate();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addCat(String category) {

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con.prepareStatement("insert into categories values (?)");
			st.setString(1, category);
			st.executeUpdate();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Category> categories() {
		List<Category> listCategory = new ArrayList<>();

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con.prepareStatement("SELECT * FROM categories ORDER BY no_categorie");
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int no_categorie = rs.getInt("no_categorie");
				String libelle = rs.getString("libelle");
				Category category = new Category(no_categorie, libelle);

				listCategory.add(category);
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listCategory;
	}

	@Override
	public void resetPw(int no_utilisateur, String email) {

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con
					.prepareStatement("update utilisateurs set mot_de_passe = ? where (no_utilisateur = ?)");
			st.setString(1, email);
			st.setInt(2, no_utilisateur);
			st.executeUpdate();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public User userCheck(String email) {

		User user = new User();

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con.prepareStatement("select * from utilisateurs where (email = ?)");
			st.setString(1, email);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				user.setNo_utilisateur(rs.getInt("no_utilisateur"));
			} else {
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public User register(User userData) {

		User userDataS = new User();

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con
					.prepareStatement("select * from utilisateurs where (pseudo = ?) OR (email = ?) ");
			st.setString(1, userData.getPseudo());
			st.setString(2, userData.getEmail());
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				userDataS = null;
			} else {
				st = con.prepareStatement("insert into utilisateurs values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
						PreparedStatement.RETURN_GENERATED_KEYS);
				st.setString(1, userData.getPseudo());
				st.setString(2, userData.getNom());
				st.setString(3, userData.getPrenom());
				st.setString(4, userData.getEmail());
				st.setString(5, userData.getTelephone());
				st.setString(6, userData.getRue());
				st.setString(7, userData.getCode_postal());
				st.setString(8, userData.getVille());
				st.setString(9, userData.getMot_de_passe());
				st.setInt(10, 100);
				st.setInt(11, 0);
				st.setString(12, "ok");
				st.executeUpdate();
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					userDataS = userData;
					userDataS.setNo_utilisateur(rs.getInt(1));
					userDataS.setCredit(100);
					userDataS.setAdministrateur(0);
					userDataS.setStatus("ok");
				}
				rs.close();
				st.close();
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDataS;
	}

	@Override
	public User connect(String pseudo, String mot_de_passe) {

		User userDataS = new User();

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con
					.prepareStatement("select * from utilisateurs where ? IN (pseudo, email) AND (mot_de_passe = ?)");
			st.setString(1, pseudo);
			st.setString(2, mot_de_passe);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				userDataS.setNo_utilisateur(rs.getInt("no_utilisateur"));
				userDataS.setPseudo(rs.getString("pseudo"));
				userDataS.setNom(rs.getString("nom"));
				userDataS.setPrenom(rs.getString("prenom"));
				userDataS.setEmail(rs.getString("email"));
				userDataS.setTelephone(rs.getString("telephone"));
				userDataS.setRue(rs.getString("rue"));
				userDataS.setCode_postal(rs.getString("code_postal"));
				userDataS.setVille(rs.getString("ville"));
				userDataS.setMot_de_passe(rs.getString("mot_de_passe"));
				userDataS.setCredit(rs.getInt("credit"));
				userDataS.setAdministrateur(rs.getInt("administrateur"));
				userDataS.setStatus(rs.getString("status"));
			} else {
				userDataS = null;
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDataS;
	}

	@Override
	public void update(User userData) {

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con.prepareStatement(
					"update utilisateurs set pseudo = ?, nom= ?, prenom= ?, email= ?, telephone= ?, rue= ?, code_postal= ?, ville= ?, mot_de_passe = ? where no_utilisateur = ?");
			st.setString(1, userData.getPseudo());
			st.setString(2, userData.getNom());
			st.setString(3, userData.getPrenom());
			st.setString(4, userData.getEmail());
			st.setString(5, userData.getTelephone());
			st.setString(6, userData.getRue());
			st.setString(7, userData.getCode_postal());
			st.setString(8, userData.getVille());
			st.setString(9, userData.getMot_de_passe());
			st.setInt(10, userData.getNo_utilisateur());
			st.executeUpdate();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int no_utilisateur) {

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con.prepareStatement("delete from utilisateurs where no_utilisateur = ?");
			st.setInt(1, no_utilisateur);
			st.executeUpdate();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// image upload method
	@Override
	public void upload(int no_utilisateur, String fileName) {

		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con
					.prepareStatement("insert into ARTICLES_VENDUS (images) values (?) where no_utilisateur = ?");
			st.setString(1, "images/" + fileName);
			st.setInt(2, no_utilisateur);
			st.executeUpdate();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setStatus(String status, int no_utilisateur) {
		try {
			Connection con = ConnectionProvider.getConnection();
			PreparedStatement st = con.prepareStatement("update UTILISATEURS set status = ? WHERE no_utilisateur = ?");
			st.setString(1, status);
			st.setInt(2, no_utilisateur);
			st.executeUpdate();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Maxime's code

	private static final String SELECT_AUCTION_BY_USER_ALL = "SELECT * FROM  ARTICLES_VENDUS WHERE no_utilisateur = ? AND date_fin_encheres > GETDATE() AND date_debut_encheres <= GETDATE()";
	private static final String SELECT_FUTUR_AUCTION_BY_USER_ALL = "SELECT * FROM  ARTICLES_VENDUS WHERE no_utilisateur = ? AND date_debut_encheres > GETDATE()";
	private static final String SELECT_FINISHED_AUCTION_BY_USER_ALL = "SELECT * FROM  ARTICLES_VENDUS WHERE no_utilisateur = ? AND date_fin_encheres < GETDATE()";

	private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY date_fin_encheres) AS Rownum, * FROM ARTICLES_VENDUS WHERE date_fin_encheres > GETDATE() AND date_debut_encheres <= GETDATE()) AS RowConstrainedResult WHERE RowNum >=? AND RowNum <? ORDER BY date_fin_encheres";
	private static final String SELECT_BY_CATEGORY = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY date_fin_encheres) AS Rownum, a.* FROM ARTICLES_VENDUS a INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie WHERE date_fin_encheres > GETDATE() AND date_debut_encheres <= GETDATE() AND libelle = ?) AS RowConstrainedResult WHERE RowNum >=? AND RowNum <? ORDER BY date_fin_encheres";
	private static final String SELECT_BY_NAME = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY date_fin_encheres) AS Rownum, * FROM ARTICLES_VENDUS WHERE date_fin_encheres > GETDATE() AND date_debut_encheres <= GETDATE() AND nom_article LIKE ? OR nom_article LIKE ? OR nom_article LIKE ?)  AS RowConstrainedResult WHERE RowNum >=? AND RowNum <? ORDER BY date_fin_encheres";

	private static final String SELECT_USER_AUCTION = "SELECT * FROM(SELECT ROW_NUMBER() OVER (ORDER BY date_fin_encheres) AS Rownum,e.* FROM ENCHERES e INNER JOIN ARTICLES_VENDUS a ON e.no_article = a.no_article WHERE e.no_utilisateur = ? AND date_fin_encheres > GETDATE() AND date_debut_encheres <= GETDATE())AS RowConstrainedResult WHERE RowNum >=? AND RowNum <?";
	private static final String SELECT_USER_AUCTION_FINISHED = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY date_fin_encheres) AS Rownum, e.* FROM ENCHERES e INNER JOIN ARTICLES_VENDUS a ON e.no_article = a.no_article WHERE e.no_utilisateur = ? AND date_fin_encheres < GETDATE())AS RowConstrainedResult WHERE RowNum >=? AND RowNum <?";
	private static final String SELECT_AUCTION_BY_USER = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY date_fin_encheres) AS Rownum,* FROM  ARTICLES_VENDUS WHERE no_utilisateur = ? AND date_fin_encheres > GETDATE() AND date_debut_encheres <= GETDATE()) AS RowConstrainedResult WHERE RowNum >=? AND RowNum <?";
	private static final String SELECT_FUTUR_AUCTION_BY_USER = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY date_fin_encheres) AS Rownum, * FROM  ARTICLES_VENDUS WHERE no_utilisateur = ? AND date_debut_encheres > GETDATE()) AS RowConstrainedResult WHERE RowNum >=? AND RowNum <?";
	private static final String SELECT_FINISHED_AUCTION_BY_USER = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY date_fin_encheres) AS Rownum, * FROM  ARTICLES_VENDUS WHERE no_utilisateur = ? AND date_fin_encheres < GETDATE())AS RowConstrainedResult WHERE RowNum >=? AND RowNum <?";

	private static final String SELECT_BY_NAME_CATAGORY = "SELECT a.* FROM ARTICLES_VENDUS a INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie WHERE date_fin_encheres > GETDATE() AND date_debut_encheres <= GETDATE() AND libelle = ? AND (nom_article LIKE ? OR nom_article LIKE ? OR nom_article LIKE ?) ORDER BY date_fin_encheres ASC;";

	private static final String SELECT_BY_NO_PRODUCT = "SELECT * FROM ARTICLES_VENDUS WHERE no_article =?;";
	private static final String SELECT_CATEGORY_BY_NO_ARTICLE = "SELECT c.* FROM CATEGORIES c INNER JOIN ARTICLES_VENDUS a ON c.no_categorie = a.no_categorie WHERE a.no_article = ?";
	private static final String SELECT_BEST_AUCTION = "SELECT MAX(montant_enchere)montant_enchere FROM ENCHERES WHERE no_article = ? GROUP BY no_article";
	private static final String SELECT_DELIVERY_BY_NO_ARTICLE = "SELECT *  FROM RETRAITS WHERE no_article = ?";
	private static final String SELECT_USER_BY_ARTICLE = "SELECT * FROM UTILISATEURS u INNER JOIN ARTICLES_VENDUS a ON u.no_utilisateur = a.no_utilisateur WHERE no_article = ?";
	private static final String SET_AUCTION_PRICE = "UPDATE ENCHERES SET montant_enchere = ? WHERE no_utilisateur = ? AND no_article = ?";
	private static final String SET_CREDIT = "UPDATE UTILISATEURS SET credit = ? WHERE no_utilisateur = ?";
	private static final String SELECT_ENCHERES_BY_USER = "SELECT * FROM ENCHERES WHERE no_utilisateur = ?";

	private static final String SELECT_ENCHERE_BY_USER_NO_ARTICLE = "SELECT * FROM ENCHERES WHERE no_utilisateur = ? AND no_article = ?";

	private static final String ADD_NEW_AUCTION = "INSERT INTO ENCHERES VALUES (?,?,?,?);";
	private static final String SELECT_USER = "select * from utilisateurs where no_utilisateur = ?";

	private static final String ADD_NEW_DELIVERY = "INSERT INTO RETRAITS VALUES (?,?,?,?);";
	private static final String SELECT_USER_WITH_BEST_AUCTION = "SELECT no_utilisateur FROM ENCHERES WHERE no_article = ? AND montant_enchere = ?";

	private static final String DELETE_DELIVERY = "DELETE FROM RETRAITS WHERE no_article = ?";
	private static final String DELETE_ARTICLE = "DELETE ARTICLES_VENDUS WHERE no_article = ?";
	private static final String DETETE_AUCTION = "DELETE ENCHERES WHERE no_article= ?";
	private static final String UPDATE_ARTICLE = "UPDATE ARTICLES_VENDUS SET nom_article = ?, description = ?, date_debut_encheres= ?, date_fin_encheres= ?, prix_initial= ?,no_utilisateur = ?, no_categorie= ? WHERE no_article = ?";
	private static final String UPDATE_DELIVERY = "UPDATE RETRAITS SET rue= ?, code_postal= ?, ville=? WHERE no_article=?";

	private static final String SELECT_LISTE_ENCHERISSEUR = "SELECT e.* FROM ARTICLES_VENDUS a INNER JOIN ENCHERES e ON a.no_article = e.no_article WHERE a.no_utilisateur = ? ORDER BY montant_enchere DESC";

	// modified to allow upload of images
	private static final String ADD_NEW_PRODUCT = "INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie, images) VALUES (?,?,?,?,?,?,?,?);";

	@Override
	public List<ProductSold> currentAuctionByUserAll(int noUser) {
		List<ProductSold> productSoldList = new ArrayList<ProductSold>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_AUCTION_BY_USER_ALL);
			pstmt.setInt(1, noUser);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductSold productSold = new ProductSold();
				productSold.setNo_article(rs.getInt("no_article"));
				productSold.setNom_article(rs.getString("nom_article"));
				productSold.setDescription(rs.getString("description"));
				productSold.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
				productSold.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
				productSold.setPrix_initial(rs.getInt("prix_initial"));
				productSold.setPrix_vente(rs.getInt("prix_vente"));
				productSold.setNo_utilisateur(rs.getInt("no_utilisateur"));
				productSold.setNo_categorie(rs.getInt("no_categorie"));
				productSold.setImages(rs.getString("images"));
				productSoldList.add(productSold);
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_AUCTION_BY_USER_ALL ");
		}
		return productSoldList;
	}

	@Override
	public List<ProductSold> futurAuctionByUserAll(int noUser) {
		List<ProductSold> productSoldList = new ArrayList<ProductSold>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_FUTUR_AUCTION_BY_USER_ALL);
			pstmt.setInt(1, noUser);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductSold productSold = new ProductSold();
				productSold.setNo_article(rs.getInt("no_article"));
				productSold.setNom_article(rs.getString("nom_article"));
				productSold.setDescription(rs.getString("description"));
				productSold.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
				productSold.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
				productSold.setPrix_initial(rs.getInt("prix_initial"));
				productSold.setPrix_vente(rs.getInt("prix_vente"));
				productSold.setNo_utilisateur(rs.getInt("no_utilisateur"));
				productSold.setNo_categorie(rs.getInt("no_categorie"));
				productSold.setImages(rs.getString("images"));
				productSoldList.add(productSold);
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_FUTUR_AUCTION_BY_USER_ALL ");
		}
		return productSoldList;
	}

	@Override
	public List<ProductSold> finishedAuctionByUserAll(int noUser) {
		List<ProductSold> productSoldList = new ArrayList<ProductSold>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_FINISHED_AUCTION_BY_USER_ALL);
			pstmt.setInt(1, noUser);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductSold productSold = new ProductSold();
				productSold.setNo_article(rs.getInt("no_article"));
				productSold.setNom_article(rs.getString("nom_article"));
				productSold.setDescription(rs.getString("description"));
				productSold.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
				productSold.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
				productSold.setPrix_initial(rs.getInt("prix_initial"));
				productSold.setPrix_vente(rs.getInt("prix_vente"));
				productSold.setNo_utilisateur(rs.getInt("no_utilisateur"));
				productSold.setNo_categorie(rs.getInt("no_categorie"));
				productSold.setImages(rs.getString("images"));
				productSoldList.add(productSold);
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_FINISHED_AUCTION_BY_USER ");
		}
		return productSoldList;
	}

	@Override
	public List<ProductSold> selectAllProducts(Pagination pagination) {
		List<ProductSold> productSoldList = new ArrayList<ProductSold>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_PRODUCTS);
			pstmt.setInt(1, pagination.getDebut());
			pstmt.setInt(2, pagination.getFin());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductSold productSold = new ProductSold();
				productSold.setNo_article(rs.getInt("no_article"));
				productSold.setNom_article(rs.getString("nom_article"));
				productSold.setDescription(rs.getString("description"));
				productSold.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
				productSold.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
				productSold.setPrix_initial(rs.getInt("prix_initial"));
				productSold.setPrix_vente(rs.getInt("prix_vente"));
				productSold.setNo_utilisateur(rs.getInt("no_utilisateur"));
				productSold.setNo_categorie(rs.getInt("no_categorie"));
				productSold.setImages(rs.getString("images"));
				productSoldList.add(productSold);

			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_ALL_PRODUCTS ");
		}
		return productSoldList;
	}

	@Override
	public List<ProductSold> selectByName(String name, Pagination pagination) {
		List<ProductSold> productSoldList = new ArrayList<ProductSold>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NAME);
			pstmt.setString(1, "%" + name + "%");
			pstmt.setString(2, name + "%");
			pstmt.setString(3, "%" + name);
			pstmt.setInt(4, pagination.getDebut());
			pstmt.setInt(5, pagination.getFin());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductSold productSold = new ProductSold();
				productSold.setNo_article(rs.getInt("no_article"));
				productSold.setNom_article(rs.getString("nom_article"));
				productSold.setDescription(rs.getString("description"));
				productSold.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
				productSold.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
				productSold.setPrix_initial(rs.getInt("prix_initial"));
				productSold.setPrix_vente(rs.getInt("prix_vente"));
				productSold.setNo_utilisateur(rs.getInt("no_utilisateur"));
				productSold.setNo_categorie(rs.getInt("no_categorie"));
				productSold.setImages(rs.getString("images"));
				productSoldList.add(productSold);
			}

		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_BY_NAME");
		}
		return productSoldList;

	}

	@Override
	public List<ProductSold> selectByCategory(String nomCategory, Pagination pagination) {
		List<ProductSold> productSoldList = new ArrayList<ProductSold>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_CATEGORY);
			pstmt.setString(1, nomCategory);
			pstmt.setInt(2, pagination.getDebut());
			pstmt.setInt(3, pagination.getFin());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductSold productSold = new ProductSold();
				productSold.setNo_article(rs.getInt("no_article"));
				productSold.setNom_article(rs.getString("nom_article"));
				productSold.setDescription(rs.getString("description"));
				productSold.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
				productSold.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
				productSold.setPrix_initial(rs.getInt("prix_initial"));
				productSold.setPrix_vente(rs.getInt("prix_vente"));
				productSold.setNo_utilisateur(rs.getInt("no_utilisateur"));
				productSold.setNo_categorie(rs.getInt("no_categorie"));
				productSold.setImages(rs.getString("images"));
				productSoldList.add(productSold);
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_BY_CATEGORIE ");
		}
		return productSoldList;
	}

	@Override
	public List<Auction> userAuctions(int noUser, Pagination pagination) {
		List<Auction> auctionList = new ArrayList<Auction>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_USER_AUCTION);
			pstmt.setInt(1, noUser);
			pstmt.setInt(2, pagination.getDebut());
			pstmt.setInt(3, pagination.getFin());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Auction auction = new Auction();
				Date date = rs.getDate("date_enchere");
				Timestamp timestamp = new Timestamp(date.getTime());
				auction.setDate_enchere(timestamp.toLocalDateTime());
				auction.setMontant_enchere(rs.getInt("montant_enchere"));
				auction.setNo_article(rs.getInt("no_article"));
				auctionList.add(auction);
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_USER_AUCTION ");
		}
		return auctionList;
	}

	@Override
	public List<Auction> userAuctionsFinished(int noUser, Pagination pagination) {
		List<Auction> auctionList = new ArrayList<Auction>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_USER_AUCTION_FINISHED);
			pstmt.setInt(1, noUser);
			pstmt.setInt(2, pagination.getDebut());
			pstmt.setInt(3, pagination.getFin());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Auction auction = new Auction();
				Date date = rs.getDate("date_enchere");
				Timestamp timestamp = new Timestamp(date.getTime());
				auction.setDate_enchere(timestamp.toLocalDateTime());
				auction.setMontant_enchere(rs.getInt("montant_enchere"));
				auction.setNo_article(rs.getInt("no_article"));
				auctionList.add(auction);
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_USER_AUCTION_FINISHED ");
		}
		return auctionList;
	}

	@Override
	public List<ProductSold> currentAuctionByUser(int noUser, Pagination pagination) {
		List<ProductSold> productSoldList = new ArrayList<ProductSold>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_AUCTION_BY_USER);
			pstmt.setInt(1, noUser);
			pstmt.setInt(2, pagination.getDebut());
			pstmt.setInt(3, pagination.getFin());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductSold productSold = new ProductSold();
				productSold.setNo_article(rs.getInt("no_article"));
				productSold.setNom_article(rs.getString("nom_article"));
				productSold.setDescription(rs.getString("description"));
				productSold.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
				productSold.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
				productSold.setPrix_initial(rs.getInt("prix_initial"));
				productSold.setPrix_vente(rs.getInt("prix_vente"));
				productSold.setNo_utilisateur(rs.getInt("no_utilisateur"));
				productSold.setNo_categorie(rs.getInt("no_categorie"));
				productSold.setImages(rs.getString("images"));
				productSoldList.add(productSold);
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_AUCTION_BY_USER ");
		}
		return productSoldList;
	}

	@Override
	public List<ProductSold> futurAuctionByUser(int noUser, Pagination pagination) {
		List<ProductSold> productSoldList = new ArrayList<ProductSold>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_FUTUR_AUCTION_BY_USER);
			pstmt.setInt(1, noUser);
			pstmt.setInt(2, pagination.getDebut());
			pstmt.setInt(3, pagination.getFin());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductSold productSold = new ProductSold();
				productSold.setNo_article(rs.getInt("no_article"));
				productSold.setNom_article(rs.getString("nom_article"));
				productSold.setDescription(rs.getString("description"));
				productSold.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
				productSold.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
				productSold.setPrix_initial(rs.getInt("prix_initial"));
				productSold.setPrix_vente(rs.getInt("prix_vente"));
				productSold.setNo_utilisateur(rs.getInt("no_utilisateur"));
				productSold.setNo_categorie(rs.getInt("no_categorie"));
				productSold.setImages(rs.getString("images"));
				productSoldList.add(productSold);
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_FUTUR_AUCTION_BY_USER ");
		}
		return productSoldList;
	}

	@Override
	public List<ProductSold> finishedAuctionByUser(int noUser, Pagination pagination) {
		List<ProductSold> productSoldList = new ArrayList<ProductSold>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_FINISHED_AUCTION_BY_USER);
			pstmt.setInt(1, noUser);
			pstmt.setInt(2, pagination.getDebut());
			pstmt.setInt(3, pagination.getFin());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductSold productSold = new ProductSold();
				productSold.setNo_article(rs.getInt("no_article"));
				productSold.setNom_article(rs.getString("nom_article"));
				productSold.setDescription(rs.getString("description"));
				productSold.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
				productSold.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
				productSold.setPrix_initial(rs.getInt("prix_initial"));
				productSold.setPrix_vente(rs.getInt("prix_vente"));
				productSold.setNo_utilisateur(rs.getInt("no_utilisateur"));
				productSold.setNo_categorie(rs.getInt("no_categorie"));
				productSold.setImages(rs.getString("images"));
				productSoldList.add(productSold);
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_FINISHED_AUCTION_BY_USER ");
		}
		return productSoldList;
	}

	@Override
	public List<ProductSold> selectByNameCategory(String nomCategory, String name) {
		List<ProductSold> productSoldList = new ArrayList<ProductSold>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NAME_CATAGORY);
			pstmt.setString(1, nomCategory);
			pstmt.setString(2, "%" + name + "%");
			pstmt.setString(3, name + "%");
			pstmt.setString(4, "%" + name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductSold productSold = new ProductSold();
				productSold.setNo_article(rs.getInt("no_article"));
				productSold.setNom_article(rs.getString("nom_article"));
				productSold.setDescription(rs.getString("description"));
				productSold.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
				productSold.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
				productSold.setPrix_initial(rs.getInt("prix_initial"));
				productSold.setPrix_vente(rs.getInt("prix_vente"));
				productSold.setNo_utilisateur(rs.getInt("no_utilisateur"));
				productSold.setNo_categorie(rs.getInt("no_categorie"));
				productSold.setImages(rs.getString("images"));
				productSoldList.add(productSold);
			}

		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_BY_NAME_CATEGORIE ");
		}
		return productSoldList;
	}

	@Override
	public ProductSold selectByNoProduct(int NoProduct) {
		ProductSold productSold = new ProductSold();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_NO_PRODUCT);
			pstmt.setInt(1, NoProduct);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				productSold.setNo_article(rs.getInt("no_article"));
				productSold.setNom_article(rs.getString("nom_article"));
				productSold.setDescription(rs.getString("description"));
				productSold.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
				productSold.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
				productSold.setPrix_initial(rs.getInt("prix_initial"));
				productSold.setPrix_vente(rs.getInt("prix_vente"));
				productSold.setNo_utilisateur(rs.getInt("no_utilisateur"));
				productSold.setNo_categorie(rs.getInt("no_categorie"));
				productSold.setImages(rs.getString("images"));
			}

		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_BY_NAME_CATEGORIE ");
		}
		return productSold;
	}

	@Override
	public Category selectCategoryByNoProduct(int NoProduct) {
		Category category = new Category();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_CATEGORY_BY_NO_ARTICLE);
			pstmt.setInt(1, NoProduct);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				category.setNo_categorie(rs.getInt("no_categorie"));
				category.setLibelle(rs.getString("libelle"));
			}

		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_CATEGORY_BY_NO_ARTICLE ");
		}
		return category;
	}

	@Override
	public DeliveryAddress selectByNoArticle(int NoProduct) {
		DeliveryAddress deliveryAddress = new DeliveryAddress();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_DELIVERY_BY_NO_ARTICLE);
			pstmt.setInt(1, NoProduct);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				deliveryAddress.setCode_postal(rs.getString("code_postal"));
				deliveryAddress.setNo_article(rs.getInt("no_article"));
				deliveryAddress.setRue(rs.getString("rue"));
				deliveryAddress.setVille(rs.getString("ville"));
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_DELIVERY_BY_NO_ARTICLE ");
		}
		return deliveryAddress;
	}

	@Override
	public User selectUserByProduct(int NoProduct) {
		User selectUserByProduct = new User();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_USER_BY_ARTICLE);
			pstmt.setInt(1, NoProduct);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				selectUserByProduct.setNo_utilisateur(rs.getInt("no_utilisateur"));
				selectUserByProduct.setPseudo(rs.getString("pseudo"));
				selectUserByProduct.setNom(rs.getString("nom"));
				selectUserByProduct.setPrenom(rs.getString("prenom"));
				selectUserByProduct.setEmail(rs.getString("email"));
				selectUserByProduct.setTelephone(rs.getString("telephone"));
				selectUserByProduct.setRue(rs.getString("rue"));
				selectUserByProduct.setCode_postal(rs.getString("code_postal"));
				selectUserByProduct.setVille(rs.getString("ville"));
				selectUserByProduct.setMot_de_passe(rs.getString("mot_de_passe"));
				selectUserByProduct.setCredit(rs.getInt("credit"));
				selectUserByProduct.setAdministrateur(rs.getInt("administrateur"));
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_USER_BY_ARTICLE ");
		}
		return selectUserByProduct;
	}

	@Override
	public void encherir(int prix, int no_user, int no_article) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SET_AUCTION_PRICE);
			pstmt.setInt(1, prix);
			pstmt.setInt(2, no_user);
			pstmt.setInt(3, no_article);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erreur BDD SET_AUCTION_PRICE ");
		}

	}

	@Override
	public void setCredit(int prix, int no_user) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SET_CREDIT);
			pstmt.setInt(1, prix);
			pstmt.setInt(2, no_user);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erreur BDD SET_CREDIT ");
		}
	}

	@Override
	public List<Auction> encheresUtilisateur(int no_user) {
		List<Auction> auctionList = new ArrayList<Auction>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCHERES_BY_USER);
			pstmt.setInt(1, no_user);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Auction auction = new Auction();
				Date date = rs.getDate("date_enchere");
				Timestamp timestamp = new Timestamp(date.getTime());
				auction.setDate_enchere(timestamp.toLocalDateTime());
				auction.setMontant_enchere(rs.getInt("montant_enchere"));
				auction.setNo_article(rs.getInt("no_article"));
				auctionList.add(auction);
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_ENCHERES_BY_USER ");
		}
		return auctionList;
	}

	@Override
	public void addNewAuction(Auction auction) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(ADD_NEW_AUCTION);
			pstmt.setInt(1, auction.getNo_utilisateur());
			pstmt.setInt(2, auction.getNo_article());
			LocalDateTime date = LocalDateTime.now();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			pstmt.setTimestamp(3, timestamp);
			pstmt.setInt(4, auction.getMontant_enchere());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erreur BDD ADD_NEW_AUCTION ");
		}
	}

	@Override
	public User selectUser(int no_utilisateur) {
		User user = new User();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_USER);
			pstmt.setInt(1, no_utilisateur);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				user.setNo_utilisateur(rs.getInt("no_utilisateur"));
				user.setPseudo(rs.getString("pseudo"));
				user.setNom(rs.getString("nom"));
				user.setPrenom(rs.getString("prenom"));
				user.setEmail(rs.getString("email"));
				user.setTelephone(rs.getString("telephone"));
				user.setRue(rs.getString("rue"));
				user.setCode_postal(rs.getString("code_postal"));
				user.setVille(rs.getString("ville"));
				user.setMot_de_passe(rs.getString("mot_de_passe"));
				user.setCredit(rs.getInt("credit"));
				user.setAdministrateur(rs.getInt("administrateur"));
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_ENCHERES_BY_USER ");
		}
		return user;
	}

	@Override
	public void addNewDelivery(DeliveryAddress deliveryAddress) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(ADD_NEW_DELIVERY);
			pstmt.setInt(1, deliveryAddress.getNo_article());
			pstmt.setString(2, deliveryAddress.getRue());
			pstmt.setString(3, deliveryAddress.getCode_postal());
			pstmt.setString(4, deliveryAddress.getVille());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erreur BDD ADD_NEW_DELIVERY ");
		}
	}

	@Override
	public Auction UserWithBestAuction(int no_article, int Max_enchere) {
		Auction auction = new Auction();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_USER_WITH_BEST_AUCTION);
			pstmt.setInt(1, no_article);
			pstmt.setInt(2, Max_enchere);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				auction.setNo_utilisateur(rs.getInt("no_utilisateur"));
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_USER_WITH_BEST_AUCTION ");
		}
		return auction;
	}

	@Override
	public Auction selectBestAuction(int NoProduct) {
		Auction auction = new Auction();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BEST_AUCTION);
			pstmt.setInt(1, NoProduct);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				auction.setMontant_enchere(rs.getInt("montant_enchere"));
			}

		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_BEST_AUCTION ");
		}
		return auction;
	}

	@Override
	public void deleteDelivery(int no_article) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_DELIVERY);
			pstmt.setInt(1, no_article);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erreur BDD DELETE_DELIVERY ");
		}
	}

	@Override
	public void deleteArticle(int no_article) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_ARTICLE);
			pstmt.setInt(1, no_article);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erreur BDD DELETE_ARTICLE ");
		}
	}

	@Override
	public void deleteAuction(int no_article) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(DETETE_AUCTION);
			pstmt.setInt(1, no_article);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erreur BDD DETETE_AUCTION ");
		}
	}

	@Override
	public void updateArticle(ProductSold product, int no_article) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_ARTICLE);
			pstmt.setString(1, product.getNom_article());
			pstmt.setString(2, product.getDescription());
			LocalDate dateDebut = product.getDate_debut_encheres();
			Date sqlDateDebut = Date.valueOf(dateDebut);
			pstmt.setDate(3, sqlDateDebut);
			LocalDate dateFin = product.getDate_fin_encheres();
			Date sqlDateFin = Date.valueOf(dateFin);
			pstmt.setDate(4, sqlDateFin);
			pstmt.setInt(5, product.getPrix_initial());
			pstmt.setInt(6, product.getNo_utilisateur());
			pstmt.setInt(7, product.getNo_categorie());
			pstmt.setInt(8, no_article);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("erreur BDD UPDATE_ARTICLE ");
		}
	}

	@Override
	public void updateDelivery(DeliveryAddress deliveryAddress, int no_article) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_DELIVERY);
			pstmt.setString(1, deliveryAddress.getRue());
			pstmt.setString(2, deliveryAddress.getCode_postal());
			pstmt.setString(3, deliveryAddress.getVille());
			pstmt.setInt(4, no_article);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erreur BDD UPDATE_DELIVERY ");
		}

	}

	@Override
	public Auction enchereUtilisateurByNoArticle(int no_user, int no_article) {
		Auction auction = new Auction();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCHERE_BY_USER_NO_ARTICLE);
			pstmt.setInt(1, no_user);
			System.out.println("bdd : " + no_user);
			pstmt.setInt(2, no_article);
			System.out.println("bdd : " + no_article);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Date date = rs.getDate("date_enchere");
				Timestamp timestamp = new Timestamp(date.getTime());
				auction.setDate_enchere(timestamp.toLocalDateTime());
				auction.setMontant_enchere(rs.getInt("montant_enchere"));
				System.out.println("bdd montant: " + rs.getInt("montant_enchere"));
				auction.setNo_article(rs.getInt("no_article"));
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_ENCHERE_BY_USER_NO_ARTICLE ");
		}
		return auction;
	}

	// updated to allow image upload
	@Override
	public void addArticle(ProductSold product) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(ADD_NEW_PRODUCT, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, product.getNom_article());
			pstmt.setString(2, product.getDescription());
			LocalDate dateDebut = product.getDate_debut_encheres();
			Date sqlDateDebut = Date.valueOf(dateDebut);
			pstmt.setDate(3, sqlDateDebut);
			LocalDate dateFin = product.getDate_fin_encheres();
			Date sqlDateFin = Date.valueOf(dateFin);
			pstmt.setDate(4, sqlDateFin);
			pstmt.setInt(5, product.getPrix_initial());
			pstmt.setInt(6, product.getNo_utilisateur());
			pstmt.setInt(7, product.getNo_categorie());
			pstmt.setString(8, product.getImages());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				product.setNo_article(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD ADD_NEW_PRODUCT ");
		}
	}

	private static final String UPDATE_PRIX_VENTE = "UPDATE ARTICLES_VENDUS SET prix_vente = ? WHERE no_article = ?";

	@Override
	public void updatePrixVente(int prix, int no_article) {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_PRIX_VENTE);
			pstmt.setInt(1, prix);
			pstmt.setInt(2, no_article);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("erreur BDD UPDATE_PRIX_VENTE ");
		}

	}

	@Override
	public List<Auction> listeEncherisseur(int no_user) {
		List<Auction> auctionList = new ArrayList<Auction>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_LISTE_ENCHERISSEUR);
			pstmt.setInt(1, no_user);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Auction auction = new Auction();
				Date date = rs.getDate("date_enchere");
				Timestamp timestamp = new Timestamp(date.getTime());
				auction.setDate_enchere(timestamp.toLocalDateTime());
				auction.setMontant_enchere(rs.getInt("montant_enchere"));
				auction.setNo_article(rs.getInt("no_article"));
				auction.setNo_utilisateur(rs.getInt("no_utilisateur"));
				auctionList.add(auction);
			}
		} catch (SQLException e) {
			System.out.println("erreur BDD SELECT_LISTE_ENCHERISSEUR ");
		}
		return auctionList;
	}
}
