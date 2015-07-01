package it.univr.models;


import java.io.Serializable;
import java.sql.Timestamp;

public class AmministrazioneModel implements Serializable
{

	/** Serial Version UID. */

	private static final long serialVersionUID = -1426065474072522810L;

	private int id;
	private String nome;
	private String cognome;
	private String username;
	private String password;
	private String email;
	private String n_cell;
	private String tipo;
	private Timestamp last_login;
	private Timestamp last_logout;

	/**
	 * Ritorna l'ID dell'Amministratore/Correttore
	 * 
	 * @return id
	 */

	public int getId()
	{
		return id;
	}

	/**
	 * Setta l'ID dell'Amministratore/Correttore
	 * 
	 * @param id
	 */
	
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Ritorna il nome dell'Amministratore/Correttore
	 * 
	 * @return nome
	 */

	public String getNome()
	{

		return nome;
	}

	/**
	 * Setta il nome dell'Amministratore/Correttore
	 * 
	 * @param nome
	 */
	
	public void setNome(String nome)
	{
		this.nome = nome;
	}

	/**
	 * Ritorna il cognome dell'Amministratore/Correttore
	 * 
	 * @return cognome
	 */

	public String getCognome()
	{
		return cognome;
	}


	/**
	 * Setta il cognome dell'Amministratore/Correttore
	 * 
	 * @param cognome
	 */
	
	public void setCognome(String cognome)
	{
		this.cognome = cognome;
	}

	/**
	 * Ritorna l'username dell'Amministratore/Correttore
	 * 
	 * @return username
	 */

	public String getUsername()
	{
		return username;
	}

	/**
	 * Setta l'username dell'Amministratore/Correttore
	 * 
	 * @param username
	 */

	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * Ritorna la password dell'Amministratore/Correttore
	 * 
	 * @return password
	 */
	
	public String getPassword()
	{
		return password;
	}

	/**
	 * Setta la password dell'Amministratore/Correttore
	 * 
	 * @param password
	 */
	
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Ritorna la mail dell'Amministratore/Correttore
	 * 
	 * @return email
	 */

	public String getEmail()
	{
		return email;
	}

	/**
	 * Ritorna la mail dell'Amministratore/Correttore
	 * 
	 * @param email
	 */
	
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * Ritorna il numero di telefono dell'Amministratore/Correttore
	 * 
	 * @return n_cell
	 */

	public String getN_cell()
	{
		return n_cell;
	}

	
	/**
	 * Setta il numero di telefono dell'Amministratore/Correttore
	 * 
	 * @param n_cell
	 */

	public void setN_cell(String n_cell)
	{
		this.n_cell = n_cell;
	}

	/**
	 * Ritorna il tipo di Amministratore o Correttore
	 * 
	 * @return tipo
	 */

	public String getTipo()
	{
		return tipo;
	}

	/**
	 * Setta il tipo Amministratore o Correttore
	 * 
	 * @param tipo
	 */

	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}

	/**
	 * Ritorna il tempo dell'ultimo login effettuato da Amministratore/Correttore
	 * 
	 * @return last_login
	 */

	public Timestamp getLast_login()
	{
		return last_login;
	}

	/**
	 * Setta il tempo dell'ultimo login effettuato da Amministratore/Correttore
	 * 
	 * @param last_login
	 */

	public void setLast_login(Timestamp last_login)
	{
		this.last_login = last_login;
	}

	/**
	 * Ritorna il tempo dell'ultimo logout effettuato da Amministratore/Correttore
	 * 
	 * @return last_logout
	 */

	public Timestamp getLast_logout()
	{
		return last_logout;
	}

	/**
	 * Setta il tempo dell'ultimo logout effettuato da Amministratore/Correttore
	 * 
	 * @param last_logout
	 */

	public void setLast_logout(Timestamp last_logout)
	{
		this.last_logout = last_logout;
	}

}
