package fr.eni.losna.bo;

public class DeliveryAddress {

	private int no_article;
	private String rue;
	private String code_postal;
	private String ville;

	public int getNo_article() {
		return no_article;
	}

	public void setNo_article(int no_article) {
		this.no_article = no_article;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public DeliveryAddress(int no_article, String rue, String code_postal, String ville) {
		super();
		this.no_article = no_article;
		this.rue = rue;
		this.code_postal = code_postal;
		this.ville = ville;
	}
	
	public DeliveryAddress(String rue, String code_postal, String ville) {
        super();
        this.rue = rue;
        this.code_postal = code_postal;
        this.ville = ville;
    }
	
	public DeliveryAddress() {
	}

	@Override
	public String toString() {
		return "DeliveryAddress [no_article=" + no_article + ", rue=" + rue + ", code_postal=" + code_postal
				+ ", ville=" + ville + "]";
	}

}
