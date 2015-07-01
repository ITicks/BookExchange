package it.univr.beans;

import it.univr.db.DataSourceUtente;
import it.univr.models.UtenteModel;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Bean per la registrazione dell'Utente.
 */

@ManagedBean
@SessionScoped
public class RegistrazioneBean implements Serializable
{
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 3677997034601234180L;

	private UtenteModel nuovoUtente;
	
	private DataSourceUtente dsUtente;
	
	public boolean registrazioneAux() {
		List<Object> list = new ArrayList<Object>();
		
		list.add(nuovoUtente.getNome());
		list.add(nuovoUtente.getCognome());
		list.add(nuovoUtente.getUsername());
		list.add(nuovoUtente.getPassword());
		list.add(nuovoUtente.getEmail());
		list.add(nuovoUtente.getIndirizzo());
		list.add(nuovoUtente.getComune());
		list.add(nuovoUtente.getProvincia());
		
		dsUtente = new DataSourceUtente();
		try
		{
			dsUtente.inserimentoUtente(list);
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
