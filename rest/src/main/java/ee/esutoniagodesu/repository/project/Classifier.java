package ee.esutoniagodesu.repository.project;

import com.jc.structure.pojo.IntIDStringTitle;
import ee.esutoniagodesu.domain.kanjidic2.table.CfReadingType;
import ee.esutoniagodesu.domain.publik.table.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * tegeleb tõlgetega, mitte sõnavaraga
 */
public enum Classifier {

	CF_AUDIO_QUALITY(CfAudioQuality.class),
	CF_VOCA_TRANS_QUALITY(CfVocaTransQuality.class),
	CF_ET_SONALIIK(CfEtSonaliik.class),
	CF_JP_CATEGORY(CfJpCategory.class),
	CF_ORIGIN(CfOrigin.class),
	CF_READING_TYPE(CfReadingType.class);

	public Class CLAZZ;

	Classifier(Class clazz) {
		this.CLAZZ = clazz;
	}

	public Map<Integer, String> asMap(ClassifierDB db) {
		Map<Integer, String> result = new LinkedHashMap<>();
		for (IntIDStringTitle p : db.findAll(this)) {
			result.put(p.getId(), p.getTitle());
		}
		return result;
	}

	public String getTitleById(ClassifierDB db, int id) {
		return db.find(this, id).getTitle();
	}

	public static Classifier findByName(String name) {
		for (Classifier p : values()) {
			if (p.name().compareToIgnoreCase(name) == 0) return p;
		}
		return null;
	}
}
