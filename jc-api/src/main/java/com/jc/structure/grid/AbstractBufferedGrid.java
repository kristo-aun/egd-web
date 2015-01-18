package com.jc.structure.grid;

import com.jc.structure.pojo.IntID;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;

/**
 * See klass ei ole paketist väljaspool kasutatav.
 * Sisaldab local ja remote grid ühisosa.
 */
abstract class AbstractBufferedGrid<T extends IntID> implements BufferedGridModel<T>, Serializable {

	private static final Logger log = Logger.getLogger(AbstractBufferedGrid.class);
    private static final long serialVersionUID = -3597836757406555223L;

    /**
	 * Kasutatakse tabeli värskendamisel / strateegia muutmisel
	 * 1) Tühjendab kõik puhvrid ja lähtestab kõik parameetrid.
	 * 2) leiab strateegiast grid size.
	 *
	 * Jätab alles komparaatori, strateegia, doRefreshAfterCommit, pageSize
	 */
	public void clearAll() {
		clearAddedToGrid();
		clearEditedInGrid();
		clearRemovedFromGrid();
		clearChecked();
		clearHighlighted();
		resetPointer();
		clearHashCodes();
		_grid.clear();
		setCurrentPageNo(1);
		_allchecked = false;
		clearGridSize();
	}

	protected abstract void clearGridSize();

	//------------------------------ andmed ------------------------------

	protected final _BidirectionalMap<Integer, T> _grid = new _BidirectionalMap<>();//rownum <-> entity
	protected int _gridSize;//orderGrid meetodis leitakse

	public final int countAll() {
		return _gridSize;
	}

	/**
	 * @return kui palju ridu on reaalselt mälus
	 */
	public int countInMemory() {
		return _grid.size();
	}

	public static final int DEFAULT_PAGE_SIZE = 20;
	protected int _pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * @return page size. Vaikimisi on lehe suurus 50
	 */
	public int getPageSize() {
		return _pageSize;
	}

	/**
	 * 1) salvestab uue lehe suuruse
	 * Kui varasemas on kõik objektid mällu laetud, siis
	 * 2) ehitab pagedGrid uue lehe suurusega üles
	 * Kui mitte, siis tühjendab pagedGrid, kuna ridade asukohad ei ole enam teada
	 * @param pageSize objekte lehel
	 * @throws IllegalArgumentException ei luba üle 1000 kirje ühele lehele
	 */
	public void setPageSize(int pageSize) throws IllegalArgumentException {
		if (pageSize < 1) throw new IllegalArgumentException("setPageSize: pageSize < 1");
		if (getPageSize() != pageSize) {
			log.debug("setPageSize: old=" + _pageSize + ", new=" + pageSize);

			//viib kasutaja sellele lehele, kus asub praeguse lehe esimene objekt
			if (getCurrentPageNo() > 1) {
				int rownum = getCurrentPageFromIdx();
				T first = getEntityByRownum(rownum);
				_pageSize = pageSize;
				setCurrentPageNo(getEntityPageNo(first));
			} else {
				_pageSize = pageSize;
			}
		}
	}

	/**
	 * Realisatsioonis tuleb valida, kas küsida väärtus siit grid.size kaudu või andembaasist
	 * @return objektide arv. Kui objekte ei ole, siis 0
	 */
	public int countPages() {
		return GridUtils.countPages(countAll(), _pageSize);
	}

	/**
	 * @param entity mällu laetud rida
	 * @return mis lehel asub antud mällu laetud rida
	 * @throws IllegalArgumentException kui sellist entity't ei ole gridis
	 */
	public int getEntityPageNo(T entity) throws IllegalArgumentException {
		if (entity == null || !_grid.containsValue(entity))
			throw new IllegalArgumentException("getEntityPageNo: ei ole gridis, entity=" + entity);
		int index = _grid.getKey(entity);
		return (index / getPageSize()) + 1;
	}

	//------------------------------ hashcode ------------------------------

	//id > 0 objektide hashcode nimekiri. kasutusel stagemata muudatuste leidmiseks
	protected Map<Integer, Integer> _hashCodes = new HashMap<>();
	public void clearHashCodes() {_hashCodes.clear();}

	protected void checkUnstagedEdits() {
		//leian hashcode järgi kas gridis on üksikuid ridu muudetud
		for (T entity : _grid.values()) {
			if (!_editedInGrid.contains(entity) &&
					entity.getId() != 0 &&
					_hashCodes.containsKey(entity.getId()) &&
					entity.hashCode() != _hashCodes.get(entity.getId())) {
				setEdited(entity);
			}
		}
	}

	public void checkHashcode(T entity) {
		if (entity.getId() > 0) _hashCodes.put(entity.getId(), entity.hashCode());
	}

	public void rebuildHashCodes() {
		clearHashCodes();
		for (T p : _grid.values()) {
			if (p.getId() > 0) _hashCodes.put(p.getId(), p.hashCode());
		}
	}

	/**
	 * @return kõikide positiivse ID'ga objektide ID nimekiri. Kasutatakse väliselt.
	 */
	public Collection<Integer> getAllPositiveIds() {
		return _hashCodes.keySet();
	}

	//------------------------------ commit operatsioonid ------------------------------

	/**
	 * @return kas on commitimata muudatusi
	 */
	public boolean isCommitAvailable() {
		return countAdded() > 0 || countRemoved() > 0 || countEdited() > 0;
	}

	/**
	 * käivitab kõik 3
	 */
	public void doPostCommit() {
		long ms = System.currentTimeMillis();
		
		//igaks juhuks tuleb puhvrid tühjendada kui seda pole tehtud implementatsioonis
		postCommitAdded();
		postCommitEdited();
		postCommitRemoved();
		rebuildHashCodes();//kui implementatsioonis või DB klassis pandi objektile id külge

		log.debug("commitChanges: time=" + (System.currentTimeMillis() - ms));
	}

	/**
	 *
	 * Tühjendab anbmebaasioperatsiooni ootavate ridade puhvri.
	 */
	public void postCommitAdded() {
		for (T p : _addedToGrid) {
			checkHashcode(p);
		}
		clearAddedToGrid();
	}

	/**
	 *
	 * Tühjendab anbmebaasioperatsiooni ootavate ridade puhvri.
	 */
	public void postCommitEdited() {
		for (T p : _editedInGrid) checkHashcode(p);
		clearEditedInGrid();
	}

	/**
	 * Tühjendab anbmebaasioperatsiooniga eemaldatud ridade puhvri.
	 */
	public void postCommitRemoved() {
		clearRemovedFromGrid();
	}

	//------------------------------ get entity operatsioonid ------------------------------

	public int getPageNoByRownum(int rownum) {
		return ((rownum - 1) / getPageSize()) + 1;
	}

	protected boolean isInMemory(T entity) {
		return _grid.containsValue(entity);
	}

	protected boolean isInMemory(int entityId) {
		return getMemoryEntityById(entityId) != null;
	}

	protected boolean isPageInMemory(int page) {
		boolean hasPage = !(page < 1 || page > countPages());
		if (!hasPage) return false;

		int[] fromTo = rowsFromTo(page);

		for (int i = fromTo[0]; i<=fromTo[1]; i++) {
			if (!_grid.containsKey(i)) return false;
		}
		return true;
	}

	protected T getMemoryEntityById(int entityId) throws IllegalArgumentException {
		if (entityId < 1) throw new IllegalArgumentException("getMemoryEntityById: entityId < 1");
        //optimeerimine. kui hascode list ei sisalda id'd, siis järelikult ei ole objekti ka mälus
        if (!_hashCodes.containsKey(entityId)) {
            log.debug("getMemoryEntityById: !hashCodes.containsKey: entityId=" + entityId);
            return null;
        }
        for (T p : _grid.values()) {
			if (p.getId() == entityId) return p;
		}
		return null;
	}

    public T getFirst() {
        return getEntityByRownum(1);
    }

    public T getLast() {
        return getEntityByRownum(countAll());
    }

	//------------------------------ lehe numbri ja lehtede haldus ------------------------------

	//alati > 0
	protected int _currentPageNo = 1;

	/**
	 * @return vaikimisi on olek esimene leht
	 */
	public int getCurrentPageNo() {
		return _currentPageNo;
	}

	/**
	 * Kui ridu ei ole, siis või ainult sisestada 1
	 * @param currentPageNo lehe number
	 * @throws IllegalArgumentException
	 */
	public void setCurrentPageNo(int currentPageNo) throws IllegalArgumentException {
		if (currentPageNo < 1 || (countPages() > 0 && currentPageNo > countPages()))
			throw new IllegalArgumentException("setCurrentPageNo=" + currentPageNo +
					": currentPageNo < 1 || currentPageNo > countPages");
		_currentPageNo = currentPageNo;
	}

	/**
	 * Viib viimasele lehele
	 */
	public void setCurrentPageNoToLast() {
		int pages = countPages();
		setCurrentPageNo(pages > 0 ? pages : 1);
	}

	public List<T> getCurrentPage() {
		return getPage(_currentPageNo);
	}

	public boolean hasPage(int page) {
		return !(page < 1 || page > countPages());
	}

	//------------------------------ järjestamise operatsioonid ------------------------------

    private static class DefaultComparator<T extends IntID> implements Comparator<T>, Serializable {
        private static final long serialVersionUID = 36188041011946940L;
        public int compare(T o1, T o2) {
            return (o1.getId() < o2.getId() ? -1 : (o1 == o2 ? 0 : 1));
        }
        public String toString() {
            return "Järjestus entity id järgi";
        }
    }

	/**
	 * Vaikimisi toimub järjestamine objekti id järgi
	 */
	protected final Comparator<T> _defaultComparator = new DefaultComparator<>();

	protected Comparator<T> _comparator = _defaultComparator;

	public Comparator<T> getComparator() {
		return _comparator;
	}

	public void restoreDefaultComparator() {
		_comparator = _defaultComparator;
	}

	/**
	 * Komparaatori haldamine implementeerivas klassis nt. enum abil
	 * @param comparator komparaator
	 * @throws IllegalArgumentException null
	 */
	public void setComparator(Comparator<T> comparator) throws IllegalArgumentException {
		if (comparator == null) throw new IllegalArgumentException("setComparator: comparator == null");
		_comparator = comparator;
	}

	protected int[] rowsFromTo(int page) {
		int pages = countPages();
		//kui lehti ei ole
		if (pages == 0 || page < 1) return new int[]{0, 0};
		//kui ei küsita viimast lehte
		int firstrow = (page * getPageSize()) - getPageSize() + 1;
		if (page < pages) return new int[]{firstrow, page * getPageSize()};
		//viimane leht
		return new int[]{firstrow, countAll()};
	}

	public List<T> getRowsFromTo(int[] fromTo) throws IllegalArgumentException {
		return getRowsFromTo(fromTo[0], fromTo[1]);
	}

	public int getRowPageNo(int rowIndex) {
		return GridUtils.getRowPageNo(rowIndex, _pageSize);
	}

	public int getCurrentPageFromIdx() {
		return rowsFromTo(getCurrentPageNo())[0];
	}

	public int getCurrentPageToIdx() {
		return rowsFromTo(getCurrentPageNo())[1];
	}

	/**
	 * 1) loob listi kõikidest pagedGrid lehtedel olevatest objektidest
	 * 2) tühjendab pagedGrid'i
	 * 3) sorteerib komparaatoriga listi
	 * 4) sublist'i abil jaotab juba sorteeritud objektid tagasi pagedGrid'i
	 *
	 * Tingimuseks on, et kõik read on mällu laetud
	 */
	public void orderGrid() {
		long ms = System.currentTimeMillis();
		if (countAll() == 0) return;

		List<T> all = new ArrayList<>(_grid.values());
		_grid.clear();

		Collections.sort(all, getComparator());

		for (T p : all) {
			_grid.put(all.indexOf(p) + 1, p);
		}

		//peale orderit
		setCurrentPageNo(1);
		log.debug("orderGrid: " + _comparator.toString() + ", time=" + (System.currentTimeMillis() - ms));
	}

	//------------------------------ gridi puhverdatud C U D operatsioonid ------------------------------

	protected List<T> _addedToGrid = new ArrayList<>();//entity id


	/**
	 * @return Uus array, mis andakse gridist välja. Uus seetõttu, et välised muudatused ei kajastuks siin
	 */
	public List<T> getAdded() {
		return new ArrayList<>(_addedToGrid);
	}

	public int countAdded() {return _addedToGrid.size();}
	public void clearAddedToGrid() {_addedToGrid.clear();}

	public void setAdded(T entity) {
		if (entity == null) throw new IllegalArgumentException("setAdded: entity == null");
		setListAdded(Arrays.<T>asList(entity));
	}

	/**
	 * Tabelisse lisamisel uued read paigutatakse lõppu.
	 * @param entities lisatavad read
	 */
	public void setListAdded(List<T> entities) throws IllegalArgumentException {
		if (entities == null) throw new IllegalArgumentException("setListAdded: entity == null");
		if (entities.size() < 1) return;

		for (T p : entities) {
			if ((p.getId() > 0 && _hashCodes.containsKey(p.getId())) || containsEntity(p))
				throw new IllegalArgumentException("setListAdded: Rida on juba gridis olemas: p=" + p);
		}

		//lisatakse added puhvrisse ja salvestatakse hashcode
		int i = 1;
		for (T p : entities) {
			_grid.put(countAll() + i, p);
			_addedToGrid.add(p);
			if (p.getId() > 0) {
                _hashCodes.put(p.getId(), p.hashCode());

                //võib-olla on lisatav rida varem eemaldatud, aga commitimata.
                for (T q : _removedFromGrid) {
                    if (p.getId() == q.getId() || p == q || p.equals(q)) {
                        log.debug("setListAdded: rida on removed puhvris, eemaldan ta sealt, p.id=" + p.getId());
                        _removedFromGrid.remove(q);
                        break;
                    }
                }
            }
			i++;
		}
		_gridSize += entities.size();
	}

	protected List<T> _editedInGrid = new ArrayList<>();//entity id

	public List<T> getEdited() {
		checkUnstagedEdits();
		return new ArrayList<>(_editedInGrid);
	}

	public int countEdited() {return getEdited().size();}
	public void clearEditedInGrid() {_editedInGrid.clear();}

	public void stageAllForCommit() {
		setListEdited(getAll());
	}

	public void setEdited(T p) throws IllegalArgumentException {
		//kui varemalt on rida lisatute puhvris, siis ei ole vaja edited staatust määrata
		if (_addedToGrid.contains(p)) return;
		//ja kui varem on sama rida muudetud, siis pole uuesti tarvis lisada
		if (!_editedInGrid.contains(p)) _editedInGrid.add(p);
		//arvutab uue hashcode
		checkHashcode(p);
	}

	public void setListEdited(List<T> entities) throws IllegalArgumentException {
		if (entities == null) throw new IllegalArgumentException("setListEdited: entity == null");
		for (T p : entities) {
			//kui varemalt on rida lisatute puhvris, siis ei ole vaja edited staatust määrata
			if (_addedToGrid.contains(p)) continue;
			//ja kui varem on sama rida muudetud, siis pole uuesti tarvis lisada
			if (!_editedInGrid.contains(p)) _editedInGrid.add(p);
		}
	}

	public void setPageEdited(int page) throws IllegalArgumentException {
		if (page < 1 || page > countPages()) throw new IllegalArgumentException("setPageEdited: entity == null");
		setListEdited(getPage(page));
	}

	protected List<T> _removedFromGrid = new ArrayList<>();

	/**
	 * Kuna removed puhvris ei hoita salvestamata ridu, ei saa teada , kas rida lisati ja seejärel eemaldati
	 * @param entity  objekt
	 * @return kas objekt on eemaldatute nimekirjas
	 */
	public boolean isRemoved(T entity) {
		return !(entity == null || entity.getId() < 1)
				&& _removedFromGrid.contains(entity);
	}

	public boolean isRemoved(int entityId) {
		if (entityId < 1) return false;
		for(T p : _removedFromGrid) {
			if (p.getId() == entityId) return true;
		}
		return false;
	}

	public List<T> getRemoved() {
		return new ArrayList<>(_removedFromGrid);
	}

	public int countRemoved() {return _removedFromGrid.size();}
	public void clearRemovedFromGrid() {_removedFromGrid.clear();}

	public void setRemoved(T entity) throws IllegalArgumentException {
		setListRemoved(Arrays.<T>asList(entity));
	}

	public void setRemoved(int entityId) throws IllegalArgumentException {
		T entity = getEntityById(entityId);
		setRemoved(entity);
	}

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
	public void setListRemoved(List<T> entities) throws IllegalArgumentException {
		if (entities == null) throw new IllegalArgumentException("setListRemoved: entities == null");
		log.debug("setListRemoved: entities.size=" + entities.size() + ", gridSize=" + _gridSize);
		if (entities.size() < 1) return;

		int lowestTouchedIdx = countAll() - 1;
		for (T p : entities) {
			if (p.getId() > 0) _hashCodes.remove(p.getId());

			int rownum = _grid.getKey(p);
			if (rownum < lowestTouchedIdx) lowestTouchedIdx = rownum;
			removeAndShiftLeft(rownum);//TODO optimeerida lehekülje eemaldamine

			//kui entity on mõnes puhvris
			_addedToGrid.remove(p);
			_editedInGrid.remove(p);
			_highlighted.remove(p);
			_checkedInGrid.remove(p);

			//salvestamata objekte ei pea removed puhvris hoidma
			if (p.getId() > 0 && !_removedFromGrid.contains(p)) _removedFromGrid.add(p);
		}

		//Kui eemaldatakse erinevatelt lehtedelt, siis peale operatsiooni minnakse kõige väiksema
		//indeksiga eemaldatud objekti lehele või kui seda lehte enam ei ole, siis viimasele lehele.
		int lowestTouchedPage = (lowestTouchedIdx / getPageSize()) + 1;
		//tõenäoliselt läks current lehe arvestus sassi
		setCurrentPageNo(Math.min(lowestTouchedPage, countPages() != 0 ? countPages() : 1));
		//kuna pointeri lehekülge ei pruugi enam olemas olla
		resetPointer();

		if (_gridSize < 1) setAllChecked(false);
	}

	protected abstract void removeAndShiftLeft(int rownum);

	/**
	 * Kui lehte pole mälus, siis laetakse andmebaasist.
	 * Seejärel eemaldatuks märkimine
	 * @param page leht, mis tuleb eemaldatuks märkida
	 */
	public void setPageRemoved(int page) throws IllegalArgumentException {
		setListRemoved(getPage(page));
	}

	/**
	 * Märgitakse kõik objektid eemaldatuks, aga eemaldamine baasist toimub
	 * realisatsioonis (commitChanges)
	 */
	public void setAllRemoved() {
		long ms = System.currentTimeMillis();

		for (T p : getAll()) {
			if (p.getId() > 0 && !_removedFromGrid.contains(p)) _removedFromGrid.add(p);
		}
		//id-ta read lähevad kaduma
		_grid.clear();

		setCurrentPageNo(1);
		_gridSize = 0;

		clearChecked();
		clearAddedToGrid();
		clearEditedInGrid();
		clearHighlighted();
		resetPointer();
		clearHashCodes();
		log.debug("setAllRemoved: all rows staged for removal, time=" + (System.currentTimeMillis() - ms));
	}

	//------------------------------ iteraatori operatsioonid ------------------------------

	protected int _pointer = 1;

	public boolean hasNext() {
		return _pointer <= countPages();
	}

	public List<T> next() {
		log.debug("next: _pointer=" + _pointer + "/" + countPages());
		return getPage(_pointer++);
	}

	public void remove() {
		log.debug("remove: _pointer=" + _pointer);
		setPageRemoved(_pointer);
	}

	public void resetPointer() {
		_pointer = 1;
	}

	//------------------------------ checkboxi mehhanism ------------------------------

	protected List<T> _checkedInGrid = new ArrayList<>();
	protected boolean _allchecked = false;

	public final List<T> getChecked() {
		if (_allchecked) {			
			return getAll();
		} else {
			return new ArrayList<>(_checkedInGrid);
		}
	}

	/**
	 * Kui rida mälus ei ole, siis ei saa ta ka olla checked
	 * @param entityId objekti id
	 * @return kas rida on checked olekus
	 */
	public final boolean isChecked(int entityId) throws IllegalArgumentException {
		if (entityId < 1) throw new IllegalArgumentException("isChecked: ei saa kontrollida id järgi kui id < 1");
		T entity = getEntityById(entityId);
		return entity != null &&
				(isAllChecked() || _checkedInGrid.contains(entity));
	}

	public final boolean isChecked(T entity) {
		return entity != null && (isAllChecked() || _checkedInGrid.contains(entity));
	}

	public final boolean isAllChecked() {
		return _allchecked;
	}

	public final boolean isSomethingChecked() {
		return _allchecked || _checkedInGrid.size() > 0;
	}

	public final boolean isPageChecked(int page) throws IllegalArgumentException {
		if (page == 1 && countPages() < 1) return false;//kuna default currentPage=1
		if (page < 1 || page > countPages()) throw new IllegalArgumentException("isPageChecked: page < 1 || page > countPages");

		//optimeerimine
		if (!isSomethingChecked()) return false;
		if (isAllChecked()) return true;

		List<T> pageItems = getPage(page);
		return pageItems.size() >= 1 && _checkedInGrid.containsAll(pageItems);
	}

	public final boolean isCurrentPageChecked() {
		return _allchecked || isPageChecked(getCurrentPageNo());
	}

	public final void setCurrentPageChecked(boolean b) {
		setPageChecked(getCurrentPageNo(), b);
	}

	public final int countCheckedOnCurrentPage() {
		return countCheckedOnPage(_currentPageNo);
	}

	public final int countCheckedOnPage(int page) {
		int result = 0;
		for (T p : getPage(page)) {
			if (isChecked(p)) result++;
		}
		return result;
	}

	public final List<T> getCheckedOnCurrentPage() {
		return getCheckedOnPage(_currentPageNo);
	}

	public final List<T> getCheckedOnPage(int page) {
		List<T> list = getPage(page);
		Iterator<T> p = list.iterator();
		while (p.hasNext()) {
			T pitem = p.next();
			if (!isChecked(pitem)) p.remove();
		}
		return list;
	}

	public final boolean isListChecked(List<T> entities) {
		return !(entities == null || entities.size() < 1) && _checkedInGrid.containsAll(entities);
	}

	public final void setAllChecked(boolean allChecked) {
		if (allChecked && countAll() < 1)
			throw new IllegalStateException("setAllChecked: ei saa panna allchecked kui ridu pole");
		log.debug("setAllChecked: allChecked=" + allChecked);
		_allchecked = allChecked;
		if (!allChecked) clearChecked();
	}

	public final void setPageChecked(int page, boolean flag) throws IllegalArgumentException {
		if (page < 1 || page > countPages())
			throw new IllegalArgumentException("setPageChecked: page < 1 || page > countPages");
		setListChecked(getPage(page), flag);
	}

	public final void setChecked(T entity, boolean flag) {
		if (entity == null || !_grid.containsValue(entity)) throw new IllegalArgumentException("setChecked: entity == null || !_grid.containsValue(entity)");
		setListChecked(Arrays.<T>asList(entity), flag);
	}

    public final void setRownumChecked(int rownum, boolean flag) throws IllegalArgumentException {
        if (rownum < 1 || rownum > countAll()) throw new IllegalArgumentException("setRownumChecked: rownum=" + rownum);
        setChecked(getEntityByRownum(rownum), flag);
    }

	public final void setChecked(int entityId, boolean flag) throws IllegalArgumentException {
		if (entityId < 1) throw new IllegalArgumentException("setChecked: entityId < 1");
		setChecked(getEntityById(entityId), flag);
	}

	public final void setListChecked(List<T> entities, boolean flag) {
		if (entities == null) throw new IllegalArgumentException("setListChecked: entity == null");
		if (entities.size() < 1) return;
		if (flag) {
			for (T i : entities) {
				if (!_checkedInGrid.contains(i)) _checkedInGrid.add(i);
			}
			if (_checkedInGrid.size() == countAll())
				_allchecked = true;
		} else {
			//kui on lipp püsti ja eemaldatakse üksikuid, siis tuleb ikkagi
			//kõik read alla tõmmata, et alla laadimata ridade olek säiliks
			if (_allchecked && _grid.size() > _checkedInGrid.size()) {
				setListChecked(getAll(), true);
			}
			_checkedInGrid.removeAll(entities);
			_allchecked = false;
		}
	}

	public final int countChecked() {return isAllChecked() ? countAll() : _checkedInGrid.size();}
	public final int countChecked(boolean flag) {
		return flag ? countChecked() : countAll() - countChecked();
	}

	public final void clearChecked() {
		_checkedInGrid.clear();
		_allchecked = false;
	}

	public final List<Integer> getCheckedIds() {
		List<Integer> result = new ArrayList<>();
		for (T p : getChecked()) {
			result.add(p.getId());
		}
		return result;
	}

	public final void setChecked(List<Integer> entityIds, boolean flag) throws IllegalArgumentException {
		if (entityIds.size() < 1) return;

		for (int p : entityIds) {
			if (!containsEntity(p))
				throw new IllegalArgumentException("setChecked: no entity with id=" + p);
		}
		for (int p : entityIds) {
			setChecked(p, flag);
		}
	}

    public final void toggleEntityChecked(T entity) {
        boolean current = isChecked(entity);
        setChecked(entity, !current);
    }

	public final void toggleEntityChecked(int entityId) {
		T entity = getEntityById(entityId);
        toggleEntityChecked(entity);
	}

	public final void togglePageChecked(int page) {
		boolean current = isPageChecked(page);
		setPageChecked(page, !current);
	}

	public final void toggleCurrentPageChecked() {
		boolean current = isCurrentPageChecked();
		setCurrentPageChecked(!current);
	}

	public final void toggleAllChecked() {
		boolean current = isAllChecked();
		if (countAll() > 0) setAllChecked(!current);
	}

	//------------------------------ esiletõstmise oleku mehhanism ------------------------------

	protected List<T> _highlighted = new ArrayList<>();

	public List<T> getHighlighted() {
		return new ArrayList<>(_highlighted);
	}

	public int[] getHighlightedRownums() {
		int[] result = new int[_highlighted.size()];
		int i = 0;
		for (T p : _highlighted) {
			result[i] = getRownumByEntity(p);
			i++;
		}
		return result;
	}

	public boolean isHighlighted(int entityId) {
		if (entityId < 1) throw new IllegalArgumentException("isHighlighted: entityId < 1");
		T entity = getEntityById(entityId);
		return entity != null && _highlighted.contains(entity);
	}

	public boolean isHighlighted(T entity) {
		return entity != null && _highlighted.contains(entity);
	}

	public void setListHighlighted(List<T> entities, boolean flag) {
		for (T p : entities) {
			setHighlighted(p, flag);
		}
	}

	public void setHighlighted(int entityId, boolean flag) throws IllegalArgumentException {
		if (entityId < 1) throw new IllegalArgumentException("setHighlighted: entityId < 1: entityId=" + entityId);
		T entity = getEntityById(entityId);
		if (entity == null) throw new IllegalArgumentException("setHighlighted: entity == null");

		if (flag) {
			if (!_highlighted.contains(entity)) _highlighted.add(entity);
		} else {
			if (_highlighted.contains(entity)) _highlighted.remove(entity);
		}
	}

	public void setHighlightedAtRownum(int rownum, boolean flag) throws IllegalArgumentException {
		if (rownum < 1 || rownum > countAll())
			throw new IllegalArgumentException("setHighlightedAtRownum: rownum < 1 || rownum > countAll");
		T entity = getEntityByRownum(rownum);
		setHighlighted(entity, flag);
	}

	public void setHighlighted(T entity, boolean flag) throws IllegalArgumentException {
		if (entity == null) throw new IllegalArgumentException("setHighlighted: entity == null");
		if (flag) {
			if (!_highlighted.contains(entity)) _highlighted.add(entity);
		} else {
			if (_highlighted.contains(entity)) _highlighted.remove(entity);
		}
	}

	public void setPageHighlighted(int page, boolean flag) throws IllegalArgumentException {
		for (T p : getPage(page)) {
			setHighlighted(p, flag);
		}
	}

	public int countHighlightedOnCurrentPage() {
		return countHighlightedOnPage(_currentPageNo);
	}

	public int countHighlightedOnPage(int page) {
		int result = 0;
		for (T p : getPage(page)) {
			if (isHighlighted(p)) result++;
		}
		return result;
	}

	public List<T> getHighlightedOnCurrentPage() {
		return getHighlightedOnPage(_currentPageNo);
	}

	public List<T> getHighlightedOnPage(int page) {
		List<T> list = getPage(page);
		Iterator<T> p = list.iterator();
		while (p.hasNext()) {
			T pitem = p.next();
			if (!isHighlighted(pitem)) p.remove();
		}
		return list;
	}

	public int countHighlighted() {return _highlighted.size();}
	public void clearHighlighted() {_highlighted.clear();}

	//------------------------------ asendamine ------------------------------

	public void replaceAtRownum(int rownum, T newEntity) {
		if (rownum < 1 || rownum > countAll() || newEntity == null)
			throw new IllegalArgumentException("replaceAtRownum: illegal operation");

		T oldEntity = getEntityByRownum(rownum);
		replace(oldEntity, newEntity);
	}

	public void replace(int entityId, T newEntity) {
		T oldEntity = getEntityById(entityId);
		replace(oldEntity, newEntity);
	}

    @Override
    public int hashCode() {
        int result = 0;
        for (T p : getAll()) {
            result = 31 * result + p.hashCode();
        }
        log.debug("hashCode: result=" + result);
        return result;
    }

    public String toString() {
        return "AbstractBufferedGrid{" +
                "_grid.size=" + _grid.size() +
                ", _gridSize=" + _gridSize +
                ", _pageSize=" + _pageSize +
                ", _hashCodes.size=" + _hashCodes.size() +
                ", _currentPageNo=" + _currentPageNo +
                ", _comparator=" + _comparator +
                ", _addedToGrid.size=" + _addedToGrid.size() +
                ", _editedInGrid.size=" + _editedInGrid.size() +
                ", _removedFromGrid.size=" + _removedFromGrid.size() +
                ", _pointer=" + _pointer +
                ", _checkedInGrid.size=" + _checkedInGrid.size() +
                ", _allchecked=" + _allchecked +
                ", _highlighted.size=" + _highlighted.size() +
                '}';
    }
}