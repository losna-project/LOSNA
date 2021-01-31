package fr.eni.losna.bo;

public class Pagination {
	private int debut;
	private int fin;
	
	public Pagination(int debut, int fin) {
		super();
		this.debut = debut;
		this.fin = fin;
	}

	public int getDebut() {
		return debut;
	}

	public void setDebut(int debut) {
		this.debut = debut;
	}

	public int getFin() {
		return fin;
	}

	public void setFin(int fin) {
		this.fin = fin;
	}
	

	
	
}
