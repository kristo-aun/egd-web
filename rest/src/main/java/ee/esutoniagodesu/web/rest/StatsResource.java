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

@RestController
@RequestMapping("/app")
public class StatsResource {

    @Inject
    private StatsService service;

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

	@RequestMapping("/rest/stats/translated_entr_ratio")
	@ResponseBody
	public List<Map<String, ?>> getTranslatedEntrRatio() {
		return service.getTranslatedEntrRatio();
	}

	@RequestMapping("/rest/stats/count_gloss_to_sum_freq")
	@ResponseBody
	public List<Map<String, ?>> getCountGlossToSumFreqRatio() {
		return service.getCountGlossToSumFreqRatio();
	}
}
