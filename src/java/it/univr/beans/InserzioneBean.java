package it.univr.beans;

import it.univr.db.DataSourceInserzione;
import it.univr.models.InserzioneModel;
import it.univr.models.UtenteModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

public class InserzioneBean implements Serializable
{
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -6484119785619390658L;
	private DataSourceInserzione dsInserzione;
	private UtenteModel utente;
	private InserzioneModel inserzione;
	
	
	/**
	 * Inizializza il bean.  
	 */
	
	@PostConstruct
	public void initialize() {
		
		this.dsInserzione = new DataSourceInserzione();
		
	}
	
	
	public List<InserzioneModel> getInserzioni(Boolean flag){
		
		List<Object> list = new ArrayList<Object>();
				
		list.add(inserzione.getId_utente());

		return flag ? dsInserzione.getInserzioniValidate(list) : 
			dsInserzione.getInserzioniNonValidate(list);
		
	}


	public UtenteModel getUtente() {
		return utente;
	}


	public void setUtente(UtenteModel utente) {
		this.utente = utente;
	}

}
