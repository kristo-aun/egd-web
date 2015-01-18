package com.jc.structure.grid;

import com.jc.structure.pojo.IntID;

import java.util.List;

/**
 * RemoteBufferedGrid'is kasutatav andmebaasiga suhtlemise strateegia raamistik (GoF strategy pattern).
 *
 * NB! strateegia implementatsioon peab olema serialiseeritav (implements Serializable), vastasel juhul ei ole
 * ka BMG serialiseeritav
 *
 * @param <T> tabeli rea tüüp, peab omama id välja
 */
public interface EntityRetrieveStrategy<T extends IntID> {
	/**
	 * @return mitu rida on andmebaasis
	 */
	public int countAll();

	/**
	 * @param entityId entity id
	 * @return kas andmebaasis on rida antud ID'ga olemas. Parem kui findbyId, kuna findById on tavaliselt kallis
	 */
	public boolean containsEntity(int entityId) throws IllegalArgumentException;

	/**
	 * @return laeb andmebaasist read (rownum abil lahendatud)
	 */
	public List<T> getRows(int from, int to) throws IllegalArgumentException;

	/**
	 *
	 * @param entityId entity id
	 * @return anbmebaasist vastava id'ga rida
	 */
	public T findById(int entityId) throws IllegalArgumentException;
}