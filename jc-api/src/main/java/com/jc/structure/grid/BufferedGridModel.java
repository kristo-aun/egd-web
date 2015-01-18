package com.jc.structure.grid;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Lehekülgedeks jaotatava tabeli mudel.
 * C,U,D tehakse tablis kohe, andmebaasimuudatused vaid commitChanges väljakutsumisel.
 * Implementatsioonis saab valida, kas lehekülgi tiritakse nõudmisel või kõik korraga.
 * NB! strateegiat ei pea implementatsioonis määrama kui korraga kõik read alla tõmmatakse.
 *
 * Vaikimisi on järjestus id järgi, ent implementatsioonis (soovitavalt konstruktoris) saab määrata komparaatori,
 * mis kutsutakse alati välja setAdded & setAllAdded sees. Lehtede laadimisel komparaator ei käivitu,
 * kuna eeldus on, et andmebaasist tulevad read õiges järjestuses.
 *
 * Commit väljakutsumise järel ridu uuesti küsima ei minda. Implementatsiooniv võib seda siiski teha.
 *
 * Kui kui kõik read on andmebaasist mällu loetud, siis lehe suuruse muutmisel või järjestuse muutmisel
 * uuesti andmebaasit ridu ei küsita.
 *
 * Säilitab checked olekut ridade kohta.
 *
 * Implementatsiooni ei tohi anda vaatele otse, mudel peab käituma adapterina.
 *
 * Highlighting - museaalidele saab määrata _highlighted olekut. Seda peaks tegema selle klassi realisatsioonis.
 * Näiteks seatakse ajutise kasutamise museaalide tabelis hightlighted olekusse museaalid, mis on tablisse lisatud,
 * aga on salvestamata. Samuti museaalid, mis ei läbinud valideerimist.
 *
 * Soovitavalt mitte kasutada parameetriga entityId meetodeid, kuna objektiga on otsing efektiivsem.
 * Väga täpselt peab jälgima, et andmebaasi sorteerimine ja kohalik komparaator toimiks ühtemoodi.
 *
 * @param <T> tabeli rea klass, millel on integer id
 */
public interface BufferedGridModel<T> extends Iterator<List<T>> {

	/**
	 * Kasutatakse tabeli värskendamisel / strateegia muutmisel
	 * 1) Tühjendab kõik puhvrid ja lähtestab kõik parameetrid.
	 * 2) leiab strateegiast grid size.
	 *
	 * Jätab alles komparaatori, strateegia, doRefreshAfterCommit, pageSize
	 */
	public void clearAll();

	//------------------------------ strateegia haldamine ------------------------------
	public int countAll();
	/**
	 * @return page size. Vaikimisi on lehe suurus 50
	 */
	public int getPageSize();
	/**
	 * 1) salvestab uue lehe suuruse
	 * Kui varasemas on kõik objektid mällu laetud, siis
	 * 2) ehitab pagedGrid uue lehe suurusega üles
	 * Kui mitte, siis tühjendab pagedGrid, kuna ridade asukohad ei ole enam teada
	 * @param pageSize objekte lehel
	 * @throws IllegalArgumentException ei luba üle 1000 kirje ühele lehele
	 */
	public void setPageSize(int pageSize) throws IllegalArgumentException;
	/**
	 * Realisatsioonis tuleb valida, kas küsida väärtus siit grid.size kaudu või andembaasist
	 * @return objektide arv. Kui objekte ei ole, siis 0
	 */
	public int countPages();
	/**
	 * @param entity mällu laetud rida
	 * @return mis lehel asub antud mällu laetud rida
	 * @throws IllegalArgumentException kui sellist entity't ei ole gridis
	 */
	public int getEntityPageNo(T entity) throws IllegalArgumentException;

	//------------------------------ hashcode ------------------------------
	//id > 0 objektide hashcode nimekiri. kasutusel stagemata muudatuste leidmiseks
	public void clearHashCodes();
	public void checkHashcode(T entity);
	public void rebuildHashCodes();
	/**
	 * @return kõikide positiivse ID'ga objektide ID nimekiri. Kasutatakse väliselt.
	 */
	public Collection<Integer> getAllPositiveIds();

	//------------------------------ commit operatsioonid ------------------------------
	/**
	 * @return kas on commitimata muudatusi
	 */
	public boolean isCommitAvailable();
	/**
	 *
	 * Tühjendab anbmebaasioperatsiooni ootavate ridade puhvri.
	 */
	public void postCommitAdded();
	/**
	 *
	 * Tühjendab anbmebaasioperatsiooni ootavate ridade puhvri.
	 */
	public void postCommitEdited();
	/**
	 * Tühjendab anbmebaasioperatsiooniga eemaldatud ridade puhvri.
	 */
	public void postCommitRemoved();

	//------------------------------ get entity operatsioonid ------------------------------
	public int getPageNoByRownum(int rownum);
	/**
	 * Kui entity asub lehel, mida ei ole mälus, siis laetakse all terve leht
	 * @param rownum rea number
	 * @return objekt etteantud real
	 * @throws IllegalArgumentException rida ei eksisteeri. NB! esimese rea võti on 0
	 */
	public T getEntityByRownum(int rownum) throws IllegalArgumentException;
	/**
	 * 1) Proovib mälust leida objekti id järgi rida. Ei tagasta rida, mis on gridist eemaldatud
	 * 2) Kui ei ole default strateegia, siis küsib strateegia käest.
	 * Ei pane üksikut rida mällu, kuna rea number ei ole teada
	 * @param entityId objekti id
	 * @return tabeli objekt mälust või baasist
	 * @throws IllegalArgumentException entityId < 1
	 */
	public T getEntityById(int entityId) throws IllegalArgumentException;
	/**
	 * Leiab rea numbri
	 * @param entity objekt
	 * @return objekti rea number tabelis
	 * @throws IllegalArgumentException NPE
	 * @throws IllegalStateException andmebaasi strateegia. Võib-olla entity on andmebaasis olemas,
	 * aga hetkel ei ole sellist kasutusjuhtu, kus peaks leidma mällu laadimata rea indeksi
	 */
	public int getRownumByEntity(T entity) throws IllegalArgumentException, IllegalStateException;
	public int getRownumByEntityId(int id) throws IllegalArgumentException, IllegalStateException;
	/**
	 * @param entityId entity id
	 * @return kas tabelis või andmebaasis entity eksisteerib. Eemaldatud oleku kontroll getEntityById sees
	 */
	public boolean containsEntity(int entityId) throws IllegalArgumentException;
	/**
	 * @param entity tabeli rida
	 * @return kas mälus või andmebaasis entity eksisteerib. Ei tagasta eemaldatud rida
	 */
	public boolean containsEntity(T entity);

	//------------------------------ lehe numbri ja lehtede haldus ------------------------------
	/**
	 * @return vaikimisi on olek esimene leht
	 */
	public int getCurrentPageNo();
	/**
	 * Kui ridu ei ole, siis või ainult sisestada 1
	 * @param currentPageNo lehe number
	 * @throws IllegalArgumentException
	 */
	public void setCurrentPageNo(int currentPageNo) throws IllegalArgumentException;
	/**
	 * Viib viimasele lehele
	 */
	public void setCurrentPageNoToLast();
	public List<T> getCurrentPage();
	public boolean hasPage(int page);
	/**
	 * Kui korraga ei tõmmata kõiki objekte alla, siis käib lehe küsimine andmebaasist.
	 * Kui kõik objektid on tabelis, siis sobib getMemoryPage käest küsida.
	 *
	 * Realisatsiooni saab kirjutada tingimuse, et kui leht on puhvris olemas, siis ei minda seda
	 * uuesti andmebaasist tirima.
	 * @param page lehe number
	 * @return lehel olevad objektid
	 */
	public List<T> getPage(int page) throws IllegalArgumentException;
	public List<T> getAll();

	//------------------------------ järjestamise operatsioonid ------------------------------

	public List<T> getRowsFromTo(int[] fromTo) throws IllegalArgumentException;
	public List<T> getRowsFromTo(int from, int to) throws IllegalArgumentException;
	public int getRowPageNo(int rowIndex);
	public int getCurrentPageFromIdx();
	public int getCurrentPageToIdx();

	//------------------------------ gridi puhverdatud C U D operatsioonid ------------------------------
	/**
	 * @return Uus array, mis andakse gridist välja. Uus seetõttu, et välised muudatused ei kajastuks siin
	 */
	public List<T> getAdded();
	public int countAdded();
	public void clearAddedToGrid();
	public void setAdded(T entity);

    public void addAfter(int rownum, T entity);
    public void addAfter(T after, T entity);

	/**
	 * Tabelisse lisamisel uued read paigutatakse lõppu.
	 * @param entities lisatavad read
	 */
	public void setListAdded(List<T> entities) throws IllegalArgumentException;
	public List<T> getEdited();
	public int countEdited();
	public void clearEditedInGrid();
	public void stageAllForCommit();
	public void setEdited(T entity) throws IllegalArgumentException;
	public void setListEdited(List<T> entities) throws IllegalArgumentException;
	public void setPageEdited(int page) throws IllegalArgumentException;
	/**
	 * Kuna removed puhvris ei hoita salvestamata ridu, ei saa teada , kas rida lisati ja seejärel eemaldati
	 * @param entity objekti id
	 * @return kas objekt on eemaldamise puhvris
	 */
	public boolean isRemoved(T entity);
	public boolean isRemoved(int entityId);
	public List<T> getRemoved();
	public int countRemoved();
	public void clearRemovedFromGrid();
	public void setRemoved(T entity) throws IllegalArgumentException;
	public void setRemoved(int entityId) throws IllegalArgumentException;
	/**
	 * Eemaldamine gridist salvestamata.
	 * 1) Ühe rea eemaldamine näiteks rea taga oleva eemaldamise nupu abil
	 * 2) Terve lehekülje eemaldamine. Näiteks gridi päises oleva checkboxi abil.
	 * 3) Gridi lehtedel surfamisel checked olukusse pandud ridade eemaldamine
	 *
	 * Peale eemaldamist peab lehe suuruse taastama, muidu lehel olevate
	 * objektide arv ei jää samaks (va viimane leht).
	 *
	 * Kui eemaldatakse erinevatelt lehtedelt, siis peale operatsiooni minnakse kõige väiksema
	 * indeksiga eemaldatud objekti lehele või kui seda lehte enam ei ole, siis viimasele lehele.
	 * @param entities eemaldatavad gridi objektid
	 * @throws IllegalArgumentException sisend on null
	 */
	public void setListRemoved(List<T> entities) throws IllegalArgumentException;
	/**
	 * Kui lehte pole mälus, siis laetakse andmebaasist.
	 * Seejärel eemaldatuks märkimine
	 * @param page leht, mis tuleb eemaldatuks märkida
	 */
	public void setPageRemoved(int page) throws IllegalArgumentException;
	/**
	 * Märgitakse kõik objektid eemaldatuks, aga eemaldamine baasist toimub
	 * realisatsioonis (commitChanges)
	 */
	public void setAllRemoved();
	//------------------------------ iteraatori operatsioonid ------------------------------
	public void resetPointer();
	//------------------------------ checkboxi mehhanism ------------------------------
	public List<T> getChecked();
	/**
	 * Kui rida mälus ei ole, siis ei saa ta ka olla checked
	 * @param entityId objekti id
	 * @return kas rida on checked olekus
	 */
	public boolean isChecked(int entityId) throws IllegalArgumentException;
	public boolean isChecked(T entity);
	public boolean isAllChecked();
	public boolean isSomethingChecked();
	public boolean isPageChecked(int page) throws IllegalArgumentException;
	public boolean isCurrentPageChecked();
	public void setCurrentPageChecked(boolean b);
	public int countCheckedOnCurrentPage();
	public int countCheckedOnPage(int page);
	public List<T> getCheckedOnCurrentPage();
	public List<T> getCheckedOnPage(int page);
	public boolean isListChecked(List<T> entities);
	public void setAllChecked(boolean allChecked);
	public void setPageChecked(int page, boolean flag) throws IllegalArgumentException;
	public void setChecked(T entity, boolean flag);
    public void setRownumChecked(int rownum, boolean flag);
	public void setChecked(int entityId, boolean flag) throws IllegalArgumentException;
	public void setListChecked(List<T> entities, boolean flag);
	public int countChecked();
	public int countChecked(boolean flag);
	public void clearChecked();
	public List<Integer> getCheckedIds();
	public void setChecked(List<Integer> entityIds, boolean flag);
    public void toggleEntityChecked(T entity);
    public void toggleEntityChecked(int entityId);
	public void togglePageChecked(int page);
	public void toggleCurrentPageChecked();
	public void toggleAllChecked();
	//------------------------------ esiletõstmise oleku mehhanism ------------------------------
	public List<T> getHighlighted();
	public int[] getHighlightedRownums();
	public boolean isHighlighted(int entityId);
	public boolean isHighlighted(T entity);
	public void setListHighlighted(List<T> entities, boolean flag);
	public void setHighlighted(int entityId, boolean flag) throws IllegalArgumentException;
	public void setHighlightedAtRownum(int rownum, boolean flag) throws IllegalArgumentException;
	public void setHighlighted(T entity, boolean flag) throws IllegalArgumentException;
	public void setPageHighlighted(int page, boolean flag) throws IllegalArgumentException;
	public int countHighlightedOnCurrentPage();
	public int countHighlightedOnPage(int page);
	public List<T> getHighlightedOnCurrentPage();
	public List<T> getHighlightedOnPage(int page);
	public int countHighlighted();
	public void clearHighlighted();
	//------------------------------ asendamine ------------------------------
	public void replace(T oldEntity, T newEntity) throws IllegalArgumentException;
	public void replaceAtRownum(int rownum, T newEntity);
	public void replace(int entityId, T newEntity);

    public T getFirst();
    public T getLast();
}