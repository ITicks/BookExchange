package it.univr.db;

import java.io.Serializable;

/**
 * Classe astratta contenente i metodi comuni a tutti i datasource. 
 */

public abstract class AbstractDataSource implements Serializable {
	
	/** Serial Version UID. */
    
	private static final long serialVersionUID = 1L;
	
    /** Accesso al database visibile solo alle sottoclassi. */
	
	protected final DBSingleton db;
	
	
	/**
	 * Costruisce un oggetto di tipo {@code AbstractDataSource}.
	 */
	
	public AbstractDataSource() {
		db = DBSingleton.getInstance();
	}

}
