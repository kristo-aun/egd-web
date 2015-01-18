package ee.esutoniagodesu.web.ctrl;

import ee.esutoniagodesu.domain.publik.view.VStats;
import ee.esutoniagodesu.service.StatsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Statistika & graafikud
 */
@Controller
@RequestMapping("/stats")
public class StatsResource {

    @Inject
    private StatsService service;

	/**
	 * Loeb kokku olemite arvud andmebaasis.
	 * Näide: Jaapanikeelseid mõisteid (JMDict.entr) on andmebaasis 170819.
	 */
	@RequestMapping("/counters")
	@ResponseBody
	public VStats counters() {
		return service.counters();
	}

	/**
	 * JMDict tõlgete arvu iseloomustav graafik. Näitab kui suur osa jaapanikeelsetest mõistetest (entr) on tõlgitud.
	 * <p/>
	 * x - count(mitu tõlget on mõistel) ehk 1,2,3,jne
	 * y - tõlgitud mõistete osakaal
	 * <p/>
	 * Näide: ühe tõlkega (gloss) mõisteid on 7471 ehk 54%.
	 */
	@RequestMapping("/translated_entr_ratio")
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
	@RequestMapping("/count_gloss_to_sum_freq")
	@ResponseBody
	public List<Map<String, ?>> getCountGlossToSumFreqRatio() {
		return service.getCountGlossToSumFreqRatio();
	}
}
