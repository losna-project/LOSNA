package fr.eni.losna.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.losna.bll.ProductManager;
import fr.eni.losna.bo.Auction;
import fr.eni.losna.bo.Category;
import fr.eni.losna.bo.DeliveryAddress;
import fr.eni.losna.bo.ProductSold;
import fr.eni.losna.bo.User;

/**
 * Servlet implementation class AuctionDetails
 */
@WebServlet("/AuctionDetails")
public class AuctionDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductManager productManager = new ProductManager();
	private static int no_article;
	private static int noUser;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Récupère le numéro de l'utilisateur courant
		User no_user = (User) request.getSession().getAttribute("loggedInUser");
		noUser = no_user.getNo_utilisateur();
		request.setAttribute("noUser", noUser);
		System.out.println("no_utilisateur" + noUser);
		//check if user is banned
		String status = no_user.getStatus();
		request.setAttribute("status", status);


		// On récupère le numéro de l'article
		if (request.getParameter("no_art") != null) {
			no_article = Integer.parseInt(request.getParameter("no_art"));
			System.out.println("numero article :" + no_article);
		}

		// On récupère la date du jour
		LocalDate today = LocalDate.now();
		request.setAttribute("today", today);

		// On récupère la date de fin d'enchere
		LocalDate finEnchere = productManager.selectByNoProduct(no_article).getDate_fin_encheres();

		boolean isBefore = finEnchere.isBefore(today);

		if (isBefore == true) {
			request.setAttribute("isBefore", 1);
		} else {
			request.setAttribute("isBefore", 0);
		}

		// On récupère le numéro de l'aquéreur
		int bestAuction = productManager.selectBestAuction(no_article).getMontant_enchere();
		int aquereur = productManager.UserWithBestAuction(no_article, bestAuction).getNo_utilisateur();
		request.setAttribute("aquereur", aquereur);

		// On récupère le nom de l'aquereur
		String nomAquereure = productManager.selectUser(aquereur).getPseudo();
		request.setAttribute("nomAquereure", nomAquereure);

		// Afficher le nom, description, mise à prix, date fin enchere, image
		ProductSold productSold = productManager.selectByNoProduct(no_article);
		request.setAttribute("product", productSold);

		// Afficher categorie
		Category category = productManager.selectCategoryByNoProduct(no_article);
		request.setAttribute("category", category);

		// Afficher la meilleure offre
		Auction auction = productManager.selectBestAuction(no_article);
		request.setAttribute("auction", auction);

		// Afficher le retrait
		DeliveryAddress deliveryAddress = productManager.selectByNoArticle(no_article);
		request.setAttribute("deliveryAddress", deliveryAddress);

		// afficher vendeur
		User user = productManager.selectUserByProduct(no_article);
		request.setAttribute("user", user);

		request.getServletContext().getRequestDispatcher("/WEB-INF/auction_details.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
if(request.getParameter("prix")!=null) {
		int prix = Integer.parseInt(request.getParameter("prix"));
		System.out.println("j'enchéri à : " + prix);

		int meilleurOffre = productManager.selectBestAuction(no_article).getMontant_enchere();
		System.out.println("la meilleur offre est : " + meilleurOffre);

		int credit = productManager.selectUser(noUser).getCredit();
		System.out.println("mon crédit est de : " + credit);

		// on regarde si l'acheteur a déjà une enchère sur cet article, si oui on la met
		// à jour sinon on en crée une nouvelle
		System.out.println("numéro d'article : " + no_article + "numero d'utilisateur : " + noUser);
		int test = productManager.enchereUtilisateurByNoArticle(noUser, no_article).getMontant_enchere();
		System.out.println("test" + test);
		Auction user = productManager.UserWithBestAuction(no_article, meilleurOffre);
		int UserWithBestAuction = user.getNo_utilisateur();
		int userCredit = productManager.selectUser(noUser).getCredit();
		int prix_initial = productManager.selectByNoProduct(no_article).getPrix_initial();

		if (test==0) {
			System.out.println("l'utilisateur n'a jamais enchéri");
			if (prix > meilleurOffre && prix <= credit && prix > prix_initial) {
				System.out.println("aucune enchère existante");
				if (meilleurOffre != 0) {
					productManager.setCredit(meilleurOffre + userCredit, UserWithBestAuction);
					System.out.println("je rembourse le dernier encheriseur");
				}
				Auction auction = new Auction(prix, no_article, noUser);
				System.out.println("J'enchéri à" + prix + "le no_article =" + no_article + "le no_user =" + noUser);
				productManager.addNewAuction(auction);
				System.out.println("l'enchère est crée");
				productManager.setCredit(credit - prix, noUser);
				System.out.println("le crédit est enlevé");

			} else {
				if (prix < meilleurOffre || prix<prix_initial) {
					PrintWriter out = response.getWriter();
					out.println("Votre prix est inférieur à l'enchère actuelle");
					out.close();
				} else if (prix > credit) {
					PrintWriter out = response.getWriter();
					out.println("Vous n'avez pas assez de crédit");
					out.close();
				}
			}

		} else if (test != 0) {
			if (prix > meilleurOffre && prix <= credit) {

				if (meilleurOffre != 0) {
					productManager.setCredit(meilleurOffre + userCredit, UserWithBestAuction);
				}
				productManager.encherir(prix, noUser, no_article);
				productManager.setCredit(credit - prix, noUser);
			} else {
				if (prix < meilleurOffre || prix<prix_initial) {
					PrintWriter out = response.getWriter();
					out.println("Votre prix est inférieur à l'enchère actuelle");
					out.close();
				} else if (prix > credit) {
					PrintWriter out = response.getWriter();
					out.println("Vous n'avez pas assez de crédit");
					out.close();
				}

			}
		}
}
		//gestion du retrait
		String retrait = request.getParameter("retrait");
		int prixVente=productManager.selectByNoProduct(no_article).getPrix_vente();
		if(retrait!=null) {
			System.out.println("prix de vente = " +prixVente );
			if(prixVente==0) {
			int meilleurOffre = productManager.selectBestAuction(no_article).getMontant_enchere();
			//Enregistrer le prix de la vente en BDD
			productManager.updatePrixVente(meilleurOffre, no_article);
			//Créditer le vendeur du montant de l'enchère
			int userCredit = productManager.selectUser(noUser).getCredit();
			productManager.setCredit(meilleurOffre+userCredit, noUser);
			}else {
				PrintWriter out = response.getWriter();
				out.println("Le retrait a déjà été effectué et votre solde crédité du montant de l'enchère");
				out.close();
			}
		}

		request.getServletContext().getRequestDispatcher("/AuctionList").forward(request, response);
	}

}
