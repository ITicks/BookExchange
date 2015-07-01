package it.univr.beans;

import it.univr.db.DataSourceAmministratore;
import it.univr.db.DataSourceCorrettoreBozza;
import it.univr.db.DataSourceStatistiche;
import it.univr.models.AmministrazioneModel;
import it.univr.models.StatisticheModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Bean per la gestione dell'amministratore.
 */

@ManagedBean
@SessionScoped
public class AmministratoreBean extends AbstractUtente implements Serializable{

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -6742179045212754138L;

	private AmministrazioneModel amministratore;
	
	private AmministrazioneModel correttore;
	
	private DataSourceAmministratore dsAmm;
	
	private DataSourceStatistiche dsStats;
	
	private DataSourceCorrettoreBozza dsCorr;
	
	/**
	 * Inizializza il bean.  
	 */
	
	@PostConstruct
	public void initialize() {
		
		dsAmm = new DataSourceAmministratore();
		dsStats = new DataSourceStatistiche();
		dsCorr = new DataSourceCorrettoreBozza();
		
	}
	
	public AmministrazioneModel getAmministratore() {
		return amministratore;
	}
	
	/**
	 * Recupero le statistiche
	 * @return le statistiche {@code StatisticheModel}
	 */
	public StatisticheModel getStatistiche() {
		return dsStats.getStatistiche();
	}

	@Override
	public Object verifyUser(String username, String password) {
		
		this.setUsername(username);
		this.setPassword(password);
		
		List<Object> list = new ArrayList<Object>();
		
		list.add(getUsername());
		list.add(getPassword());
			
		amministratore = dsAmm.getAmministratore(list);
		
		if (amministratore == null)
           return null;
		else{
			// Aggiorno il timestamp del last login
			list = new ArrayList<Object>();
			list.add(amministratore.getId());
			dsAmm.updateLastLogin(list);			
			
			//Ritorno l'oggetto amministratore per indicare
			//l'avvenuto login
			return amministratore;
		}
	}

	public String logout() {
		if(amministratore!=null){
			// Aggiorno il timestamp del last logout
			List<Object> list = new ArrayList<Object>();
			list.add(amministratore.getId());
			dsAmm.updateLastLogout(list);
		}	
		
		return super.logout();
	}

	public boolean isLoggedIn() {
		return this.getLoggedIn(amministratore);
	}

	@Override
	public void cleanUp() {
		cleanFields();
		
		this.amministratore = null;
		this.correttore = null;
	}

	public void setNuovoCorrettore(){
		List<Object> list = new ArrayList<Object>();
		
		list.add(correttore.getNome());
		list.add(correttore.getCognome());
		list.add(correttore.getUsername());
		list.add(correttore.getPassword());
		list.add(correttore.getEmail());
		list.add(correttore.getN_cell());
		
		dsCorr.insertCorrettoreBozza(list);
	}
	
	public void deleteCorrettore( ){
		List<Object> list = new ArrayList<Object>();
		
		list.add(correttore.getId());
		
		dsCorr.deleteCorrettoreBozza(list);
	}
}
