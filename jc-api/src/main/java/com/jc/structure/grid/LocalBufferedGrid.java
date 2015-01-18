package com.jc.structure.grid;

import com.jc.structure.pojo.IntID;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class LocalBufferedGrid<T extends IntID> extends AbstractBufferedGrid<T> implements BufferedGridModel<T>, Serializable {

	private static final Logger log = Logger.getLogger(LocalBufferedGrid.class);
    private static final long serialVersionUID = 4134473270229374055L;

    public LocalBufferedGrid() {
		this(null);
	}

	protected void clearGridSize() {
		_gridSize = 0;
	}

    public int hashCode() {
        return super.hashCode();
    }

	public LocalBufferedGrid(List<T> entities) {
		int size = entities != null ? entities.size() : 0;
		log.debug("LocalBufferedGrid: new instance: entities.size=" + size);
		if (entities != null && entities.size() > 0) setEntities(entities);
	}

	/**
	 * Tabeli üles ehitamine ilma uute kirjete pending staatuseta.
	 * @param entities tabeli read
	 */
	public void setEntities(List<T> entities) throws IllegalArgumentException {
		if (entities == null)
			throw new IllegalArgumentException("setEntities: entities == null");
		clearAll();//tühjendan puhvrid

		for (T p : entities) {
			if (p.getId() > 0 && _hashCodes.containsKey(p.getId()))
				throw new IllegalStateException("setEntities: selle id'ga entity on juba mälus(" + p.getId() + ")" );

			if (p.getId() > 0) _hashCodes.put(p.getId(), p.hashCode());
			_grid.put(entities.indexOf(p) + 1, p);
		}

		_gridSize = entities.size();
		log.debug("setEntities: entities.size=" + entities.size() + ", gridSize=" + _gridSize +
                ", pageSize=" + _pageSize + ", countPages=" + countPages());
	}

	/**
	 * @param rownum rea number
	 * @return objekt etteantud real
	 * @throws IllegalArgumentException rida ei eksisteeri. NB! esimese rea võti on 0
	 */
	public final T getEntityByRownum(int rownum) throws IllegalArgumentException {
		if (rownum < 1 || rownum > countAll())
			throw new IllegalArgumentException("getEntityByRownum: rownum < 1 || rownum > countAll, rownum=" + rownum);
		T result = _grid.get(rownum);
		if (result == null)
            throw new IllegalArgumentException("getEntityByRownum: probleem rea numbriga, rownum=" + rownum);
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
	public final T getEntityById(int entityId) throws IllegalArgumentException {
		if (entityId < 1) throw new IllegalArgumentException("getEntityById: entityId < 1");
		T entity = getMemoryEntityById(entityId);
		if (entity == null) throw new IllegalArgumentException("getEntityById: entity == null");
		return entity;
	}

	/**
	 * Leiab rea numbri
	 * @param entity objekt
	 * @return objekti rea number tabelis
	 * @throws IllegalArgumentException andmebaasi strateegia. Võib-olla entity on andmebaasis olemas,
	 * aga hetkel ei ole sellist kasutusjuhtu, kus peaks leidma mällu laadimata rea indeksi
	 */
	public final int getRownumByEntity(T entity) throws IllegalArgumentException, IllegalStateException {
		if (entity == null) throw new IllegalArgumentException("getRownumByEntity: entity == null");
		if (isInMemory(entity)) {
			return _grid.getKey(entity);
		} else {
			throw new IllegalArgumentException("getRownumByEntity: entity not in memory, e=" + entity);
		}
	}

	public final int getRownumByEntityId(int id) throws IllegalArgumentException, IllegalStateException {
		if (id < 1) throw new IllegalArgumentException("getRownumByEntityId: id < 1");
		T entity = getEntityById(id);
		if (entity == null) throw new IllegalArgumentException("getRownumByEntityId: no entity by that id, id=" + id);
		return _grid.getKey(entity);
	}

	public boolean containsEntity(int entityId) throws IllegalArgumentException {
		return isInMemory(entityId);
	}

	public boolean containsEntity(T entity) {
		return isInMemory(entity);
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
	public final List<T> getPage(int page) throws IllegalArgumentException {
		if (page == 1 && countAll() == 0) return new ArrayList<>();//optimeerimine
		if (page < 1 || page > countPages())
			throw new IllegalArgumentException("getPage=" + page + ": page < 1 || page > countPages");
		List<T> result = new ArrayList<>(getPageSize());
		int[] fromTo = rowsFromTo(page);

		for (int i = fromTo[0]; i<=fromTo[1]; i++) {
			result.add(_grid.get(i));
		}

		log.debug("getPage: from=" + fromTo[0] + ", to=" + fromTo[1] + ", _grid.size=" + _grid.size());
		return result;
	}

	public final List<T> getAll() {
		List<T> result = new ArrayList<>(countAll());
		for (int i = 1 ; i <= countAll(); i++) {
			result.add(_grid.get(i));//rea numbri järgi, et järjekord õige oleks
		}
		return result;
	}

	public final List<T> getRowsFromTo(int from, int to) throws IllegalArgumentException {
		if (from < 1 || to < from || to > countAll())
			throw new IllegalArgumentException("getRowsFromTo: from < 1 || to < from || to > countAll");

		StringBuilder msg = new StringBuilder("getRowsFromTo: from=" + from + ", to=" + to);

		log.debug(msg.toString());

		List<T> result = new ArrayList<>();
		if (_grid.containsKey(from) && _grid.containsKey(to)) {
			for (int i = from; i <= to; i++) {
				if (_grid.containsKey(i))
					result.add(_grid.get(i));
				else
					throw new IllegalStateException(msg.append(", i=").append(i).toString());
			}
		} else
			throw new IllegalStateException(msg.toString());

		return result;
	}

	/**
	 * Gridist rea eemaldamisel peab järgnevaid elemente ühe võrra vasakule nihutama
	 * @param rownum rea number
	 */
	protected void removeAndShiftLeft(int rownum) {
		_grid.remove(rownum);
		_gridSize = _gridSize - 1;

		for (int i = rownum + 1; i <= _grid.size() + 1; i++) {
			_grid.put(i - 1, _grid.remove(i));
		}
	}

	//------------------------------ asendamine ------------------------------

	public void replace(T oldEntity, T newEntity) throws IllegalArgumentException {
		if (log.isDebugEnabled()) log.debug("replace: old.id=" + oldEntity.getId() + ", new.id=" + newEntity.getId());
		if (oldEntity == null) throw new IllegalArgumentException("replace: oldEntity == null");
		if (newEntity == null) throw new IllegalArgumentException("replace: newEntity == null");

		if (!isInMemory(oldEntity)) throw new IllegalArgumentException("replace: !isInMemory(oldEntity)");
		if (isInMemory(newEntity) && (getRownumByEntity(newEntity) != getRownumByEntity(oldEntity)))
			throw new IllegalArgumentException("replace: isInMemory(newEntity)");

		if ((newEntity.getId() > 0 && oldEntity.getId() != newEntity.getId() && containsEntity(newEntity.getId())))
			throw new IllegalArgumentException("replace: uue id ei ole sama mis vanal, aga uue entity id on gridis juba olemas");

		int rn = getRownumByEntity(oldEntity);

		//vana eemaldamine
		if (oldEntity.getId() > 0) _hashCodes.remove(oldEntity.getId());
		_grid.remove(rn);
		//uue panemine
		_grid.put(rn, newEntity);

		//kui entity on mõnes puhvris
		if (_addedToGrid.contains(oldEntity)) {
			_addedToGrid.remove(oldEntity);
			setAdded(newEntity);
		} else {
			_editedInGrid.remove(oldEntity);
			setEdited(newEntity);
		}

		if (_highlighted.contains(oldEntity)) {
			_highlighted.remove(oldEntity);
			setHighlighted(newEntity, true);
		}

		if (_allchecked || _checkedInGrid.contains(oldEntity)) {
			_checkedInGrid.remove(oldEntity);
			setChecked(newEntity, true);
		}
	}

    //------------------------------ nihutamine ------------------------------

    public void addAfter(int rownum, T entity) {
        if (rownum < 1 || rownum > _gridSize || entity == null) throw new IllegalStateException("addAfter: rownum=" + rownum);

        if ((entity.getId() > 0 && _hashCodes.containsKey(entity.getId())) || containsEntity(entity))
            throw new IllegalArgumentException("addAfter: Rida on juba gridis olemas: entity=" + entity);

        //alustades tagantpoolt, tõstan iga rea ühe võrra edasi
        for (int i = _gridSize; i > rownum; i--) {
            _grid.put(i + 1, _grid.remove(i));
        }

        _grid.put(rownum + 1, entity);

        //tegemist uue objektiga
        _addedToGrid.add(entity);
        if (entity.getId() > 0) _hashCodes.put(entity.getId(), entity.hashCode());

        //võib-olla on lisatav rida varem eemaldatud, aga commitimata.
        if (_removedFromGrid.contains(entity)) _removedFromGrid.remove(entity);

        _gridSize = _gridSize + 1;
    }

    public void addAfter(T after, T entity) {
        int rownum = getRownumByEntity(after);
        addAfter(rownum, entity);
    }
}