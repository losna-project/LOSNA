package fr.eni.losna.utility;

import java.util.List;
import java.util.stream.Collectors;

import fr.eni.losna.bll.ProductManager;
import fr.eni.losna.bll.UserManager;
import fr.eni.losna.bo.ProductSold;

public class Batch {
	
	public static void batch() {
		
		ProductManager productManager = new ProductManager();

		String sender = "losna.manager@gmail.com";
		String pwsender = "passwordlosna";
		String subject = "LOSNA Auctions";
		String message = "";

		// select all auctions finished today
		List<ProductSold> listAuctions = productManager.auctionByDate();
		List<Integer> articleList = listAuctions.stream().map(ProductSold::getNo_article).collect(Collectors.toList());
		// articleList.forEach(System.out::println);

		for (Integer item : articleList) {
			// select top bid value
			int bestOffer = productManager.selectBestAuction(item).getMontant_enchere();

			if (bestOffer != 0) {
				// set the sale price and get article name
				String article = productManager.selectByNoProduct(item).getNom_article();
				productManager.updatePrixVente(bestOffer, item);
				// select auction winner
				int topBuyer = productManager.UserWithBestAuction(item, bestOffer).getNo_utilisateur();
				// send email
				String pseudo = UserManager.userEmail(topBuyer).getPseudo();
				String email = UserManager.userEmail(topBuyer).getEmail();
				System.out.println(email);
				message = "Hello, " + pseudo + ".You have won an auction for " + article + ". Congratlations!";
				System.out.println(message);

				Mailer.send(sender, pwsender, email, subject, message);
				System.out.println("Email sent");
			} else {
			}
		}
	}
}
