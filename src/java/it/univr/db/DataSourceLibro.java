package it.univr.db;


import it.univr.exceptions.DatabaseException;
import it.univr.models.LibroModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DataSource che gestisce le query dei Libri.
 */

public class DataSourceLibro extends AbstractDataSource
{

	/** Serial Version UID. */
	private static final long serialVersionUID = 2687086721206469849L;

	private String inserisce_libro = "INSERT INTO Libro(ISBN, autore, "
			+ "titolo, genere, descrizione, anno, casa_editrice, "
			+ "lingua, pagine, foto, n_visualizzazioni) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";

	private String cancella_libro = "DELETE FROM Libro WHERE codice = ?";

	private String libro = "SELECT codice, ISBN, autore, titolo,"
			+ "genere, descrizione, anno, casa_editrice, lingua, pagine, "
			+ "foto, n_visualizzazioni FROM Libro WHERE codice = ? ";
	
	private String codice_libro = "SELECT codice FROM Libro WHERE ISBN = ? AND Titolo = ?";
	
	private String incrementa_nvis = "UPDATE Libro SET n_visualizzazioni = (n_visualizzazioni + 1) WHERE codice = ?";
	
	private String ricercaLibro = "SELECT codice, ISBN, autore, titolo, "
			+ "genere, descrizione, anno, casa_editrice, lingua, pagine, "
			+ "foto, n_visualizzazioni FROM Libro WHERE ";
	

	public boolean deleteLibro(List<Object> list) {
		return db.executeUpdate(cancella_libro, list) != 0;
	}

	public boolean insertLibro(List<Object> list){
		return db.executeUpdate(inserisce_libro, list) != 0;
	}

	private LibroModel makeLibroModel(ResultSet rs)
	{
		LibroModel book = new LibroModel();

		try
		{

			book.setCodice(rs.getInt("codice"));
			book.setISBN(rs.getString("ISBN"));
			book.setAutore(rs.getString("autore"));
			book.setTitolo(rs.getString("titolo"));
			book.setGenere(rs.getString("genere"));
			book.setDescrizione(rs.getString("descrizione"));
			book.setAnno(rs.getDate("anno"));
			book.setCasa_editrice(rs.getString("casa_editrice"));
			book.setLingua(rs.getString("lingua"));
			book.setPagine(rs.getInt("pagine"));
			book.setFoto(rs.getString("foto"));
			book.setN_visualizzazioni(rs.getInt("n_visualizzazioni"));

		} catch (SQLException e)
		{
			throw new DatabaseException();
		}

		return book;
	}

	public LibroModel getLibro(List<Object> list)
	{

		LibroModel book = null;
		ResultSet rs = db.executeQuery(libro, list);

		try
		{
			// Se rs.next() vale true significa che è stata estratta almeno
			// una riga.
			if (rs.next())
				book = makeLibroModel(rs);
		} catch (SQLException e)
		{
			throw new DatabaseException();
		}

		return book;
	}

	public int getCodiceLibro(List<Object> list){
		int res = -1;

		ResultSet rs = db.executeQuery(codice_libro, list);

		try {
			if (rs.next())
				res = rs.getInt("codice");
		} catch (SQLException e) {
			throw new DatabaseException();
		}

		return res;
	}
	
	public boolean incrementNVis(List<Object> list){
		return db.executeUpdate(incrementa_nvis, list) != 0;
	}
	
	public List<LibroModel> ricercaLibri(List<Object> temp){
		String[] vett = {" genere = ? ", " titolo = ? ", " autore = ? "};
		List<LibroModel> result = new ArrayList<LibroModel>();
		
		List<Object> list = new ArrayList<Object>();
		
		String tempRicercaLibro = ricercaLibro;
		
		//Costruisco le condizioni della query in automatico
		for(Object obj : temp)
			if(obj!=null)
				if(!obj.equals("") && !obj.equals("NESSUNO")){
					if(list.size()>0) tempRicercaLibro += "AND";
					tempRicercaLibro += vett[temp.indexOf(obj)];
					list.add(obj);
				}
		
		ResultSet rs = db.executeQuery(tempRicercaLibro, list);
		
		try {

			while (rs.next())
				result.add(makeLibroModel(rs));
		}
		catch (SQLException e) {
			throw new DatabaseException();
		}
		
		return result;
	}
}
