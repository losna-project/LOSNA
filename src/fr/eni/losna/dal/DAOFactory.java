package fr.eni.losna.dal;

public abstract class DAOFactory {
	public static LosnaDAO getLosnaDAO() {
		return new LosnaDAOImpl();
	}

}
