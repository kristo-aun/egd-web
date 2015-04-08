package ee.esutoniagodesu.service;

import ee.esutoniagodesu.domain.ac.table.User;
import ee.esutoniagodesu.domain.freq.table.TofuSentence;
import ee.esutoniagodesu.repository.domain.freq.TofuSentenceRepository;
import ee.esutoniagodesu.repository.project.FreqRepository;
import ee.esutoniagodesu.util.PaginationUtil;
import ee.esutoniagodesu.util.persistence.ProjectDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class TofuService {

    private static final Logger log = LoggerFactory.getLogger(TofuService.class);

    @Inject
    private ProjectDAO dao;

    @Inject
    private FreqRepository freqRepository;

    @Inject
    private TofuSentenceRepository tofuSentenceRepository;

    @Inject
    private KuromojiService kuromojiService;

    public void save(TofuSentence tofu, User user) {
        log.debug("save: tofu=" + tofu);
        dao.save(tofu);
    }

    public TofuSentence findById(int id, User user) {
        return freqRepository.findById(id, user.getLogin());
    }

    public Page<TofuSentence> getTofusByUser(int offset, int limit, User user) {
        Page<TofuSentence> result = tofuSentenceRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));

        for (TofuSentence p : result) {
            p.setTranslation(freqRepository.getTofuSentenceTranslation(p.getId(), user.getLogin()));
        }

        return result;
    }
}
