package fr.eni.losna.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import fr.eni.losna.bll.ProductManager;
import fr.eni.losna.bo.Category;
import fr.eni.losna.bo.DeliveryAddress;
import fr.eni.losna.bo.ProductSold;
import fr.eni.losna.bo.User;

/**
 * Servlet implementation class New_auction
 */
@WebServlet("/NewAuction")
//add the annotation in order to let it recognize and support multipart/form-data requests and thus get getPart() to work
@MultipartConfig
public class NewAuction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ProductManager productManager = new ProductManager();
	private static int noUser;
	private static int no_article=0;
	private static LocalDate today = LocalDate.now();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loggedInUser") == null) {
			request.getServletContext().getRequestDispatcher("/Login").forward(request, response);
		} else {
			
			// Récupère le numéro de l'utilisateur courant
			User no_user = (User) request.getSession().getAttribute("loggedInUser");
			noUser = no_user.getNo_utilisateur();
			request.setAttribute("noUser", noUser);
			System.out.println("no_utilisateur" + noUser);
			//check if the user is banned
			String status = no_user.getStatus();
			request.setAttribute("status", status);
			
			List<Category> listCatagory = productManager.categories();
			request.setAttribute("listCategory", listCatagory);
			
			if (status.equals("ban")) {
				request.getServletContext().getRequestDispatcher("/AuctionList").forward(request, response);
			} else {
			
			if (request.getParameter("no_art") != null) {
				no_article = Integer.parseInt(request.getParameter("no_art"));
				System.out.println("numero article :" + no_article);

				// Préremplir retrait
				String article = productManager.selectByNoProduct(no_article).getNom_article();
				String description = productManager.selectByNoProduct(no_article).getNom_article();
				int categorie = productManager.selectByNoProduct(no_article).getNo_categorie();
				int prix = productManager.selectByNoProduct(no_article).getPrix_initial();
				LocalDate debut = productManager.selectByNoProduct(no_article).getDate_debut_encheres();
				LocalDate fin = productManager.selectByNoProduct(no_article).getDate_fin_encheres();
				int no_vendeur = productManager.selectByNoProduct(no_article).getNo_utilisateur();
				String images = productManager.selectByNoProduct(no_article).getImages();

				request.setAttribute("article", article);
				request.setAttribute("description", description);
				request.setAttribute("categorie", categorie);
				request.setAttribute("prix", prix);
				request.setAttribute("debut", debut);
				request.setAttribute("fin", fin);
				request.setAttribute("no_vendeur", no_vendeur);
				request.setAttribute("images", images);
				System.out.println("num vendeur" + no_vendeur);

				
				// Vérification date debut est après la date du jour
				boolean isBefore = debut.isBefore(today);
				if (isBefore != true) {
					request.setAttribute("isBefore", 1);
				}

			}

			// Préremplir retrait
			if (request.getParameter("no_art") == null) {
				User address = productManager.selectUser(noUser);
				String userRue = address.getRue();
				String userCodePostal = address.getCode_postal();
				String userVille = address.getVille();
				request.setAttribute("userRue", userRue);
				request.setAttribute("userCodePostal", userCodePostal);
				request.setAttribute("userVille", userVille);
			}

			request.getServletContext().getRequestDispatcher("/WEB-INF/new_auction.jsp").forward(request, response);
		}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// créer un nouvel article
		String nom = request.getParameter("article");
		String description = request.getParameter("description");
		LocalDate dateDebut = LocalDate.parse(request.getParameter("dateDebut"));
		LocalDate dateFin = LocalDate.parse(request.getParameter("dateFin"));
		int prix = Integer.parseInt(request.getParameter("prix"));
		int categorie = Integer.parseInt(request.getParameter("categorie"));

		boolean isBefore = dateDebut.isBefore(dateFin);

		String choix = request.getParameter("choix");
		
		//-------------------------------------------------------------------------------------
		//upload image code
		Part filePart = request.getPart("photo"); // Retrieves <input type="file" name="photo">
		String fileName = filePart.getName();
		System.out.println(fileName);
		InputStream fileContent = filePart.getInputStream(); // obtains input stream of the upload file
		//System.out.println(fileContent);

		File uploads = new File("?????\\images");
		
		//create an empty temporary file using the uploaded image's name and get it's newly generated name 
		File file = File.createTempFile(fileName, ".jpg", uploads);
		String images = ("images/"+file.getName());
		System.out.println(images);
		//write file data from input stream into the temporary file
		Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		
		// -------------------------------------------------------------------------------------
		
		if (choix.equals("Enregistrer")) {

			if (isBefore == true) {
				ProductSold productSold = new ProductSold(nom, description, dateDebut, dateFin, prix, noUser,
						categorie, images);

				// créer une nouvelle adresse
				String rue = request.getParameter("rue");
				String codePostal = request.getParameter("codePostal");
				String ville = request.getParameter("ville");

				DeliveryAddress updateAddress = new DeliveryAddress(rue, codePostal, ville);
				
				if (no_article != 0) {
					System.out.println("no_article : " + no_article );
					System.out.println("Je modifie la vente");
					productManager.updateArticle(productSold, no_article);
					System.out.println(rue + codePostal +ville+no_article );
					productManager.updateDelivery(updateAddress, no_article);
					//Je réinitialise le no_article à 0
					no_article=0;
				} else {
					System.out.println("Je crée une vente");
					productManager.addArticle(productSold);
					
					// Récupérer le numéro de l'article crée
					int noArticle = productSold.getNo_article();
					System.out.println("num article" + noArticle);
					
					//ajouter adresse
					DeliveryAddress address = new DeliveryAddress(noArticle, rue, codePostal, ville);
					productManager.addNewDelivery(address);

				}

			} else {
				PrintWriter out = response.getWriter();
				out.print("La date de fin d'enchère doit être inférieure à la date de début de l'enchère");
				out.close();
			}
		}

		else if (choix.equals("Supprimer")) {
			productManager.deleteSold(no_article);
		}

		request.getServletContext().getRequestDispatcher("/AuctionList").forward(request, response);

	}
}
