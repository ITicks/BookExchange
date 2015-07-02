package it.univr.db;


import it.univr.exceptions.DatabaseException;
import it.univr.models.UtenteModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DataSource che gestisce le query degli Utenti Standard.
 */

public class DataSourceUtente extends AbstractDataSource
{
	/** Serial Version UID. */

	private static final long serialVersionUID = -6914318858510827761L;

	private String utente = "SELECT id, nome, cognome, username, "
			+ "password, email, indirizzo, comune, provincia, regione, "
			+ "foto, n_cell, confermato FROM Utente "
			+ "WHERE username = ? AND password = ?";
	
	private String utenteId = "SELECT id, nome, cognome, username, "
			+ "password, email, indirizzo, comune, provincia, regione, "
			+ "foto, n_cell, confermato FROM Utente "
			+ "WHERE id = ?";

	private String lista_utenti = "SELECT * " + "FROM Utente ";

	private String inserimento_utente = "INSERT INTO Utente(id, nome, cognome, "
			+ "username, password, email, indirizzo, comune, "
			+ "provincia, regione, foto, n_cell, confermato) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'false')";

	private String modifica_utente = "UPDATE Utente "
			+ "SET nome = ? cognome = ? email = ? indirizzo = ? comune = ? "
			+ "provincia = ? regione = ? foto = ? n_cell = ? " + "WHERE id = ?";

	private String cancella_utente = "DELETE FROM Utente " + "WHERE id=?";

	private String modifica_foto_utente = "UPDATE Utente SET foto = ? WHERE username = ?";


	private UtenteModel makeUserModel(ResultSet rs)
	{
		UtenteModel user = new UtenteModel();

		try
		{

			user.setId(rs.getInt("id"));
			user.setNome(rs.getString("nome"));
			user.setCognome(rs.getString("cognome"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setIndirizzo(rs.getString("indirizzo"));
			user.setComune(rs.getString("comune"));
			user.setProvincia(rs.getString("provincia"));
			user.setRegione(rs.getString("regione"));
			user.setFoto(rs.getString("foto"));
			user.setN_cell(rs.getString("n_cell"));
			user.setConfermato(rs.getBoolean("confermato"));

		} catch (SQLException e)
		{
			throw new DatabaseException();
		}

		return user;

	}


	public UtenteModel checkUtente(List<Object> list)
	{

		UtenteModel user = null;
		ResultSet rs = db.executeQuery(utente, list);
				
		try
		{
			// Se rs.next() vale true significa che è stata estratta almeno
			// una riga.
			if (rs.next())
				user = makeUserModel(rs);
		} catch (SQLException e)
		{
			throw new DatabaseException();
		}

		return user;
	}


	public List<UtenteModel> getListaUtenti()
	{

		ResultSet result = null;
		List<UtenteModel> lista_user = new ArrayList<UtenteModel>();

		try
		{
			result = db.executeQuery(lista_utenti);
			while (result.next())
			{
				lista_user.add(makeUserModel(result));
			}

		} catch (SQLException e)
		{
			throw new DatabaseException();

		}

		return lista_user;
	}


	public boolean modificaUtente(List<Object> list) throws SQLException
	{
		return db.executeUpdate(modifica_utente, list) != 0;
	}

	public boolean cancellaUtente(List<Object> list) throws SQLException
	{
		return db.executeUpdate(cancella_utente, list) != 0;
	}

	public boolean inserimentoUtente(List<Object> list) throws SQLException
	{
		return db.executeUpdate(inserimento_utente, list) != 0;
	}

	public UtenteModel getUtente(List<Object> list) {
		ResultSet rs = db.executeQuery(utenteId, list);
				
		try {
			if (rs.next())
				return makeUserModel(rs);
		} catch (SQLException e) {
			throw new DatabaseException();
		}

		return null;
	}


	
	public boolean modificaFoto(List<Object> list) {
		return db.executeUpdate(modifica_foto_utente , list) != 0;
	}
}
