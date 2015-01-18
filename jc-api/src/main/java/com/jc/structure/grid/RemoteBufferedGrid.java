package com.jc.structure.grid;

import com.jc.structure.pojo.IntID;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
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
public final class RemoteBufferedGrid<T extends IntID> extends AbstractBufferedGrid<T> implements BufferedGridModel<T>, Serializable {

	private static final Logger log = Logger.getLogger(RemoteBufferedGrid.class);
	private static final long serialVersionUID = -6165876658914114167L;

	public RemoteBufferedGrid(EntityRetrieveStrategy<T> strategy) {
		log.debug("RemoteBufferedGrid: new instance with custom row strategy");
		_strategy = strategy;
		_gridSize = strategy.countAll();
	}

	//------------------------------ strateegia haldamine ------------------------------

	private EntityRetrieveStrategy<T> _strategy;

	/**
	 * Strateegia muutmisel peab gridi tühjendama
	 * @param strategy ridade leidmise strateegia
	 * @param pageSize lehe suurus
	 * @throws IllegalArgumentException
	 */
	private void setStrategy(EntityRetrieveStrategy<T> strategy, int pageSize) throws IllegalArgumentException {
		if (strategy == null || pageSize < 1 || pageSize > 1000)
			throw new IllegalArgumentException("setStrategy: strategy == null || pageSize < 1 || pageSize > 1000");

		log.debug("setStrategy: ridade saamise strateegia määramine");
		_strategy = strategy;
		clearAll();//tirib alla ainult countAll

		if (getPageSize() != pageSize) setPageSize(pageSize);
	}

	public void setStrategy(EntityRetrieveStrategy<T> strategy) throws IllegalArgumentException {
		setStrategy(strategy, getPageSize());
	}

	public EntityRetrieveStrategy<T> getStrategy() {
		return _strategy;
	}

	/**
	 * Andmebaasist kõikide objektide leidmine
	 */
	public List<T> findAll() {
		return _strategy.getRows(1, _strategy.countAll());
	}

	public void retrieveAll() {
		int i = 1;
		for (T p : findAll()) {
			if (p.getId() < 1) throw new IllegalStateException("retrieveAll: ilma id'ta rida");

			if (!_grid.containsKey(i)) {
				_hashCodes.put(p.getId(), p.hashCode());
				_grid.put(i, p);
			}
			i++;
		}

		if (_gridSize != _grid.size())
			throw new IllegalStateException("retrieveAll: strateegia countAll != findAll.size");

		_gridSize = _grid.size();
	}

	/**
	 * Kui on salvestamata ridu, siis võib mälus olev tabel ka suurem olla.
	 * Peab võtma arvesse salvestamata lisatud/eemaldatud ridade arvu.
	 * @return kas kõik read on andmebaasist mällu laetud
	 */
	public boolean isAllRetrieved() {
		int strategyCountAll = _strategy.countAll();
		int countInMemory = countInMemory();

		//kui on uus grid või andmebaasis ridu pole, siis järelikult on kõik vajalik info mälus olemas
		boolean b = strategyCountAll < 1;
		if (b) return true;

		//tõene vaid nullist alustatud gridi puhul
		b = strategyCountAll < 1 && countInMemory + countRemoved() - countAdded() == 0;
		if (b) return true;

		//Mälus olevate ridade arvust tuleb maha lahutada lisamise puhver.
		//Kuna eemaldamise puhvrisse lisatakse ainult id'ga ridu on seal vaid andmebaasis ekisteerivad read.
		b = countInMemory - countAdded() + countRemoved() >= strategyCountAll;
		return b;
	}

	protected void clearGridSize() {
		_gridSize = _strategy.countAll();
	}

	//------------------------------ get entity operatsioonid ------------------------------

	/**
	 * Kui entity asub lehel, mida ei ole mälus, siis laetakse all terve leht.
     * Võib-olla on mõistlikum ainult üks rida alla tõmmata, aga siis peaks strateegias getByRownum realiseerima
	 * @param rownum rea number
	 * @return objekt etteantud real
	 * @throws IllegalArgumentException rida ei eksisteeri. NB! esimese rea võti on 0
	 */
	public T getEntityByRownum(int rownum) throws IllegalArgumentException {
		if (rownum < 1 || rownum > countAll())
			throw new IllegalArgumentException("getEntityByRownum: rownum < 1 || rownum > countAll, rownum=" + rownum);

		if (!_grid.containsKey(rownum)) {
			int page = getPageNoByRownum(rownum);
			getPage(page);
		}

		T result = _grid.get(rownum);

		if (result == null) throw new IllegalStateException("getEntityByRownum: probleem rea numbriga, rownum=" + rownum);

		return result;
	}

	/**
	 * 1) Proovib mälust leida objekti id järgi rida. Ei tagasta rida, mis on gridist eemaldatud
	 * 2) Kui ei ole default strateegia, siis küsib strateegia käest.
	 * Ei pane üksikut rida mällu, kuna rea number ei ole teada
	 * @param entityId objekti id
	 * @return tabeli objekt mälust või baasist
	 * @throws IllegalArgumentException entityId < 1
	 */
	public T getEntityById(int entityId) throws IllegalArgumentException {
		if (entityId < 1) throw new IllegalArgumentException("getEntityById: entityId < 1");

		//kõigepealt otsib mälust
		T entity = getMemoryEntityById(entityId);
		if (entity == null && !isRemoved(entityId)) {
			//ei salvesta mällu
			return _strategy.findById(entityId);
		} else {
			return entity;//siit võib ka null objekti saada
		}
	}

	/**
	 * Leiab rea numbri
	 * @param entity objekt
	 * @return objekti rea number tabelis
	 * @throws IllegalArgumentException NPE
	 * @throws IllegalStateException andmebaasi strateegia. Võib-olla entity on andmebaasis olemas,
	 * aga hetkel ei ole sellist kasutusjuhtu, kus peaks leidma mällu laadimata rea indeksi
	 */
	public int getRownumByEntity(T entity) throws IllegalArgumentException, IllegalStateException {
		if (entity == null) throw new IllegalArgumentException("getRownumByEntity: entity == null");
		if (isInMemory(entity)) {
			return _grid.getKey(entity);
		} else {
			throw new IllegalStateException("getRownumByEntity: entity not in memory, e=" + entity);
		}
	}

	public int getRownumByEntityId(int id) throws IllegalArgumentException, IllegalStateException {
		if (id < 1) throw new IllegalArgumentException("getRownumByEntityId: id < 1");
		T entity = getEntityById(id);
		if (entity == null) throw new IllegalArgumentException("getRownumByEntityId: no entity by that id, id=" + id);

		if (isInMemory(entity)) {
			return _grid.getKey(entity);
		} else {
			throw new IllegalStateException("getRownumByEntity: entity not in memory, e=" + entity);
		}
	}

	/**
	 * @param entityId entity id
	 * @return kas tabelis või andmebaasis entity eksisteerib. Eemaldatud oleku kontroll getEntityById sees
	 */
	public boolean containsEntity(int entityId) throws IllegalArgumentException {
		if (entityId < 1) throw new IllegalArgumentException("containsEntity: entityId < 1");

		return getMemoryEntityById(entityId) != null ||
				(!isRemoved(entityId) && _strategy.findById(entityId) != null);

	}

	/**
	 * @param entity tabeli rida
	 * @return kas mälus või andmebaasis entity eksisteerib. Ei tagasta eemaldatud rida
	 */
	public boolean containsEntity(T entity) {
		return entity != null &&
				(_grid.containsValue(entity) ||
						(entity.getId() > 0 && !isRemoved(entity) && _strategy.containsEntity(entity.getId())));
	}

	//------------------------------ lehe numbri ja lehtede haldus ------------------------------

	protected void removeAndShiftLeft(int rownum) {
		throw new RuntimeException("not implemented");
	}

	/**
	 * Kui korraga ei tõmmata kõiki objekte alla, siis käib lehe küsimine andmebaasist.
	 * Kui kõik objektid on tabelis, siis sobib getMemoryPage käest küsida.
	 *
	 * Realisatsiooni saab kirjutada tingimuse, et kui leht on puhvris olemas, siis ei minda seda
	 * uuesti andmebaasist tirima.
	 * @param page lehe number
	 * @return lehel olevad objektid
	 */
	public List<T> getPage(int page) throws IllegalArgumentException {
		if (page == 1 && countAll() == 0) return new ArrayList<>();//optimeerimine
		if (page < 1 || page > countPages())
			throw new IllegalArgumentException("getPage=" + page + ": page < 1 || page > countPages");
		log.debug("getPage: page=" + page);
		long ms = System.currentTimeMillis();
		List<T> result = new ArrayList<>();
		int[] fromTo = rowsFromTo(page);

		if (isPageInMemory(page)) {
			log.debug("getPage: hasPage");
			for (int i = fromTo[0]; i<=fromTo[1]; i++) {
				result.add(_grid.get(i));
			}
		} else {//remote strateegia, lehte ei ole mälus
			result.addAll(downloadToBuffer(fromTo));
		}

		log.debug("getPage: from=" + fromTo[0] + ", to=" + fromTo[1] +
				", _grid.size=" + _grid.size() + ", time=" + (System.currentTimeMillis() - ms));
		return result;
	}

    private List<T> downloadToBuffer(int[] fromTo) {
        return downloadToBuffer(fromTo[0], fromTo[1]);
    }

    private List<T> downloadToBuffer(int from, int to) {
        List<T> result = new ArrayList<>();
        int i = 0;
        List<T> db = _strategy.getRows(from, to);
        for (T p : db) {
            try {
                int key = from + i;
                //kui muudetakse lehe suurust ntx 20->50, siis esimesel lehel 20 on mälus, aga 30 ei ole.
                //tiriti alla 50tk, aga esimesed 20 on samad.
                if (!_grid.containsKey(key)) {
                    if (p.getId() < 1 || (p.getId() > 0 && _hashCodes.containsKey(p.getId())))
                        throw new IllegalStateException("getPage: id < 1 || selle id'ga entity on juba mälus: p.id=" + p.getId() +
                                ", entity=" + getMemoryEntityById(p.getId()));
                    _hashCodes.put(p.getId(), p.hashCode());
                    _grid.put(key, p);
                    if (_allchecked) setChecked(p, true);
                }
            } catch (IllegalStateException e) {//topelt kirje
                log.error("getPage: from="+from +", to=" + to+
                        ", db.size=" + db.size() + ", rn=" + (from + i), e);
                throw e;
            }
            result.add(p);
            i++;
        }
        return result;
    }

	public List<T> getAll() {
		if (!isAllRetrieved()) {
			log.warn("getAll: isAllRetrieved=false, will download all entities");
			retrieveAll();
		}

		List<T> result = new ArrayList<>(countAll());
		for (int i = 1 ; i <= countAll(); i++) {
			result.add(_grid.get(i));
		}
		return result;
	}

	public List<T> getRowsFromTo(int from, int to) throws IllegalArgumentException {
		if (from < 1 || to < from || to > countAll())
			throw new IllegalArgumentException("getRowsFromTo: from < 1 || to < from || to > countAll");

		log.debug("getRowsFromTo: from=" + from + ", to=" + to);

		List<T> result = new ArrayList<>();
		try {
			if (_grid.containsKey(from) && _grid.containsKey(to)) {
				for (int i = from; i <= to; i++) {
					if (_grid.containsKey(i))
						result.add(_grid.get(i));
					else
						throw new IllegalStateException();
				}
			} else
				throw new IllegalStateException();
		} catch (IllegalStateException e) {
			result = _strategy.getRows(from, to);
			if (result.size() != (to-from+1))
				throw new RuntimeException("getRowsFromTo: strateegia tagastas vale arvu ridu");

		}
		return result;
	}

	public void replace(T oldEntity, T newEntity) throws IllegalArgumentException {
		throw new RuntimeException("not implemented");
	}

	public void reloadById(int entityId) {
		int rownum = getRownumByEntityId(entityId);//viskab vea kui ei ole
		T entity = _strategy.findById(entityId);
		_hashCodes.put(entityId, entity.hashCode());
		_grid.put(rownum, entity);
	}

	public void reloadByRownum(int entityId) {
		throw new RuntimeException("not implemented");
	}

    //------------------------------ nihutamine ------------------------------

    public void addAfter(int rownum, T entity) {
        throw new RuntimeException("not implemented");
    }

    public void addAfter(T after, T entity) {
        throw new RuntimeException("not implemented");
    }
}