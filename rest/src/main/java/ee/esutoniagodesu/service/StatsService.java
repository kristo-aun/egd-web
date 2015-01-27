package ee.esutoniagodesu.service;

import com.jc.hibernate.ProjectDAO;
import ee.esutoniagodesu.domain.core.view.VCoreStats;
import ee.esutoniagodesu.domain.publik.view.VStats;
import ee.esutoniagodesu.repository.project.ReportDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Statistika & graafikud
 */
@Service
@Transactional
public class StatsService {

    private static final Logger log = LoggerFactory.getLogger(StatsService.class);

    @Inject
    private ProjectDAO dao;

    @Inject
    private ReportDB reportDB;

	private static VStats vstats;
	private static final int _lifetime = 1000 * 60 * 60 * 2;//2h

	private boolean isExpired() {
		return vstats == null || new Date().getTime() - vstats.created.getTime() > _lifetime;
	}

	private void refresh() {
		log.debug("refresh");
		vstats = reportDB.getStats();
		vstats.jmdictVersion = "27.01.2014";
	}

	/**
	 * Loeb kokku olemite arvud andmebaasis.
	 * Näide: Jaapanikeelseid mõisteid (JMDict.entr) on andmebaasis 170819.
	 */
	public VStats counters() {
		if (isExpired()) refresh();
		return vstats;
	}

	/**
	 * JMDict tõlgete arvu iseloomustav graafik. Näitab kui suur osa jaapanikeelsetest mõistetest (entr) on tõlgitud.
	 * <p/>
	 * x - count(mitu tõlget on mõistel) ehk 1,2,3,jne
	 * y - tõlgitud mõistete osakaal
	 * <p/>
	 * Näide: ühe tõlkega (gloss) mõisteid on 7471 ehk 54%.
	 */
	public List<Map<String, ?>> getTranslatedEntrRatio() {
		return reportDB.getTranslatedEntrRatio();
	}

	/**
	 * Sagedustabelit iseloomustav graafik. Näitab kui suur osa sagedustabelist on tõlgitud.
	 * <p/>
	 * x - summa(sagedustabelis oleva sõna tõlgete arv) eesti sõnastikus
	 * y - selle summa osakaal.
	 * <p/>
	 * Näide: 0 tõlkega sõnu on sagedustabelis 10463308 ehk 55%.
	 */
	public List<Map<String, ?>> getCountGlossToSumFreqRatio() {
		return reportDB.getCountGlossToSumFreqRatio();
	}

    public List<VCoreStats> getCoreStats() {
        return dao.findAll(VCoreStats.class);
    }
}
