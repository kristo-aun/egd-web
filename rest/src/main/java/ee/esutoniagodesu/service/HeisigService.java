package ee.esutoniagodesu.service;

import com.jc.hibernate.ProjectDAO;
import com.jc.lang.alphab.LatinAlphabet;
import ee.esutoniagodesu.domain.heisig.view.VHeisig6Custom;
import ee.esutoniagodesu.repository.project.Heisig4DB;
import ee.esutoniagodesu.repository.project.Heisig6DB;
import ee.esutoniagodesu.repository.project.KanjiDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class HeisigService {

    private static final Logger log = LoggerFactory.getLogger(HeisigService.class);

    @Inject
    private ProjectDAO dao;

    @Inject
    private KanjiDB kanjiDB;

    @Inject
    private Heisig4DB heisig4DB;

    @Inject
    private Heisig6DB heisig6DB;

    private static enum QueryType {
		FRAME, SENTENCE, KEYWORD
	}

    public VHeisig6Custom getHeisig6(int id) {
        return dao.find(VHeisig6Custom.class, id);
    }

    public List<VHeisig6Custom> getAll() {
        return getCollection(6, null);
    }

	public List<VHeisig6Custom> getCollection(int book, String query) {
		StringBuilder msg = new StringBuilder("rtk: book=" + book + ", query=" + query);
		if ((book != 6 && book != 4) || query == null) throw new IllegalArgumentException(msg.toString());
		log.debug(msg.toString());

		QueryType querytype;
		int frame = -1;
		String keyword = null;
		char[] signs = null;

		try {
			frame = Integer.parseInt(query);
			querytype = QueryType.FRAME;
		} catch (Exception ignored) {
			try {
				if (LatinAlphabet.hasLatin(query.substring(0, 1))) {
					querytype = QueryType.KEYWORD;
					keyword = query;
				} else {
					throw new IllegalArgumentException(msg.append(", does not consist of latin characters").toString());
				}
			} catch (Exception ignored2) {
				signs = query.toCharArray();
				querytype = QueryType.SENTENCE;
			}
		}

		switch (querytype) {
			case SENTENCE:
				return findBySigns(signs);
			case KEYWORD:
				return findByKeyword(book, keyword);
			case FRAME:
				return findByFrame(book, frame);
			default:
				throw new IllegalStateException(msg.toString());
		}
	}

	private static int limitCount(int cards) {
		if (cards <= 3) return 20;
		if (cards <= 6) return 10;
		if (cards <= 9) return 7;
		return 5;
	}

	private boolean _exampleWordsDisabled;

	public boolean isExampleWordsDisabled() {
		return _exampleWordsDisabled;
	}

	public void setExampleWordsDisabled(boolean b) {
		_exampleWordsDisabled = b;
	}

	private List<VHeisig6Custom> addExampleWords(List<VHeisig6Custom> cards) {
		if (_exampleWordsDisabled) return cards;

		for (VHeisig6Custom p : cards) {
			p.setExampleWords(kanjiDB.getExampleWords(p.getKanji(), limitCount(cards.size())));
		}
		return cards;
	}

	private List<VHeisig6Custom> findBySigns(char[] signs) {
		List<VHeisig6Custom> result = new ArrayList<>();
		List<Character> checked = new ArrayList<>();

		for (char p : signs) {
			if (!checked.contains(p) && kanjiDB.containsKanji(p)) {
				VHeisig6Custom item = heisig6DB.findHeisig6ByKanji(p);
				if (item == null) {
					log.debug("findBySigns: no heisig6 found: p=" + p);
					item = new VHeisig6Custom();
					item.setKanji(String.valueOf(p));
					item.setStrokeImageId(kanjiDB.getStrokeDiagramImageId(p));
				}

				result.add(item);
				checked.add(p);
			}
		}
		return addExampleWords(result);
	}

	private List<VHeisig6Custom> findByFrame(int book, int frame) {
		Character kanji = null;
		if (book == 6) {
			kanji = heisig6DB.findKanjiByFrame(frame);
		} else if (book == 4) {
			kanji = heisig4DB.findKanjiByFrame(frame);
		}

		if (kanji == null) {
            return new ArrayList<>();
        }

		return addExampleWords(kanjiToHeisig(kanji));
	}

	private List<VHeisig6Custom> findByKeyword(int book, String keyword) {
		List<Character> kanjis = null;
		if (book == 6) {
			kanjis = heisig6DB.findKanjisByKeyword(keyword);
		} else if (book == 4) {
			kanjis = heisig4DB.findKanjisByKeyword(keyword);
		}
		return addExampleWords(kanjiToHeisig(kanjis));
	}

	private List<VHeisig6Custom> kanjiToHeisig(char kanji) {
		return kanjiToHeisig(Arrays.asList(kanji));
	}

	private List<VHeisig6Custom> kanjiToHeisig(List<Character> chars) {
		char[] arr = new char[chars.size()];
		for (int i = 0; i < chars.size(); i++) {
			arr[i] = chars.get(i);
		}
		return findBySigns(arr);
	}
}
