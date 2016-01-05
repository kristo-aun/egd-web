package ee.esutoniagodesu.service;

import org.junit.Test;

import javax.inject.Inject;

public class StatsServiceTest extends AbstractAuthenticatedServiceTest {

    @Inject
    private StatsService statsService;

    @Test
    public void t01_getTranslatedEntrRatio() {
        statsService.getTranslatedEntrRatio();
    }
}
