package ee.esutoniagodesu.service;

import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.domain.freq.table.TofuSentence;
import ee.esutoniagodesu.repository.project.FreqRepository;
import ee.esutoniagodesu.util.GridUtils;
import ee.esutoniagodesu.util.persistence.ProjectDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class TofuService {

    private static final Logger log = LoggerFactory.getLogger(TofuService.class);

    @Inject
    private ProjectDAO dao;

    @Inject
    private FreqRepository freqRepository;

    @Inject
    private KuromojiService kuromojiService;

    public void save(TofuSentence tofu, User user) {
        log.debug("save: tofu=" + tofu);

        dao.save(tofu);
    }

    public List<TofuSentence> getTofusByUser(int page, int size, User user) {
        int[] rows = GridUtils.rowsFromTo(page, size, 4671);
        return freqRepository.findUserTofus(rows[0], rows[1], user.getLogin());
    }
}
