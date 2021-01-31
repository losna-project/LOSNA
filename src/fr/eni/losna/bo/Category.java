package fr.eni.losna.bo;

public class Category {
	private int no_categorie;
	private String libelle;

	public int getNo_categorie() {
		return no_categorie;
	}

	public void setNo_categorie(int no_categorie) {
		this.no_categorie = no_categorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Category(int no_categorie, String libelle) {
		super();
		this.no_categorie = no_categorie;
		this.libelle = libelle;
	}

	public Category() {
	}

	@Override
	public String toString() {
		return "Category [no_categorie=" + no_categorie + ", libelle=" + libelle + "]";
	}

}
