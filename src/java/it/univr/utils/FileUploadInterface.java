package it.univr.utils;

public interface FileUploadInterface {
	
	/**
	 * Eseguo l'upload in memoria del file
	 */
	public void upload();
	
	/**
	 * Salvo il file su filesystem, nella directory locale o remota.
	 * @param nomefile nome del file da salvare
	 */
	public void save(String nomefile);
}
