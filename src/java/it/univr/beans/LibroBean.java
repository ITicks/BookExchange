package it.univr.beans;

import it.univr.db.DataSourceLibro;
import it.univr.models.LibroModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Bean del Libro. 
 */

@ManagedBean
@SessionScoped
public class LibroBean implements Serializable {

    /** Serial Version UID. */
	private static final long serialVersionUID = 6464215260521061434L;

	/** Il Libro. */
	private LibroModel libro;
	
	private DataSourceLibro dsLibro;
	
	/** Inizializza il bean */
	@PostConstruct
	public void initialize() { dsLibro = new DataSourceLibro(); }

	public LibroModel getLibro(int codice){
		List<Object> list = new ArrayList<Object>();
		list.add(codice);
		dsLibro.incrementNVis(list);
		this.libro = dsLibro.getLibro(list);
		return libro;
	}
	
	public LibroModel getLibro() {
		List<Object> list = new ArrayList<Object>();
		list.add(libro.getCodice());
		dsLibro.incrementNVis(list);
		
		return libro;
	}

	public void setLibro(LibroModel libro) { this.libro = libro; }

}
