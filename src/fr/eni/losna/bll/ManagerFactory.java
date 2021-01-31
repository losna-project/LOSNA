package fr.eni.losna.bll;

public abstract class ManagerFactory {
	
	public static UserManager getUserManager() {
		
		return UserManager.getInstance();
	}	
}
