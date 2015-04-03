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
@RequestMapping("/api/stats")
public class StatsResource {

    @Inject
    private StatsService service;

	@RequestMapping("/counters")
	@ResponseBody
	public VStats counters() {
		return service.counters();
	}

    @RequestMapping("/core_stats")
    @ResponseBody
    public List<VCoreStats> getCoreStats() {
        return service.getCoreStats();
    }

	@RequestMapping("/translated_entr_ratio")
	@ResponseBody
	public List<Map<String, ?>> getTranslatedEntrRatio() {
		return service.getTranslatedEntrRatio();
	}

	@RequestMapping("/count_gloss_to_sum_freq")
	@ResponseBody
	public List<Map<String, ?>> getCountGlossToSumFreqRatio() {
		return service.getCountGlossToSumFreqRatio();
	}
}
