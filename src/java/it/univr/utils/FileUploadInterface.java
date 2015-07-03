package it.univr.utils;

import java.util.List;

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

	public String normalizeUploadFileName(List<String> strnameparts);

	public String getFilename();
}
