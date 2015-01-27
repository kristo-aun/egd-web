package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.domain.core.view.VCoreStats;
import ee.esutoniagodesu.domain.publik.view.VStats;
import ee.esutoniagodesu.service.StatsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Statistika & graafikud
 */
@RestController
@RequestMapping("/app")
public class StatsResource {

    @Inject
    private StatsService service;

	/**
	 * Loeb kokku olemite arvud andmebaasis.
	 * Näide: Jaapanikeelseid mõisteid (JMDict.entr) on andmebaasis 170819.
	 */
	@RequestMapping("/rest/stats/counters")
	@ResponseBody
	public VStats counters() {
		return service.counters();
	}

    @RequestMapping("/rest/stats/core_stats")
    @ResponseBody
    public List<VCoreStats> getCoreStats() {
        return service.getCoreStats();
    }

	/**
	 * JMDict tõlgete arvu iseloomustav graafik. Näitab kui suur osa jaapanikeelsetest mõistetest (entr) on tõlgitud.
	 * <p/>
	 * x - count(mitu tõlget on mõistel) ehk 1,2,3,jne
	 * y - tõlgitud mõistete osakaal
	 * <p/>
	 * Näide: ühe tõlkega (gloss) mõisteid on 7471 ehk 54%.
	 */
	@RequestMapping("/rest/stats/translated_entr_ratio")
	@ResponseBody
	public List<Map<String, ?>> getTranslatedEntrRatio() {
		return service.getTranslatedEntrRatio();
	}

	/**
	 * Sagedustabelit iseloomustav graafik. Näitab kui suur osa sagedustabelist on tõlgitud.
	 * <p/>
	 * x - summa(sagedustabelis oleva sõna tõlgete arv) eesti sõnastikus
	 * y - selle summa osakaal.
	 * <p/>
	 * Näide: 0 tõlkega sõnu on sagedustabelis 10463308 ehk 55%.
	 */
	@RequestMapping("/rest/stats/count_gloss_to_sum_freq")
	@ResponseBody
	public List<Map<String, ?>> getCountGlossToSumFreqRatio() {
		return service.getCountGlossToSumFreqRatio();
	}
}
