package ee.esutoniagodesu.service;

import ee.esutoniagodesu.bean.ProjectDAO;
import ee.esutoniagodesu.domain.core.table.TofuSentence;
import ee.esutoniagodesu.domain.core.table.TofuSentenceTranslation;
import ee.esutoniagodesu.pojo.test.compound.FilterCompoundSubmitDTO;
import ee.esutoniagodesu.repository.domain.freq.TofuSentenceRepository;
import ee.esutoniagodesu.repository.project.CoreDB;
import ee.esutoniagodesu.security.SecurityUtils;
import ee.esutoniagodesu.security.permission.CustomPermissionEvaluator;
import ee.esutoniagodesu.security.permission.Permission;
import ee.esutoniagodesu.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TofuService {

    private static final Logger log = LoggerFactory.getLogger(TofuService.class);

    @Inject
    private ProjectDAO dao;

    @Inject
    private CoreDB coreDB;

    @Inject
    private TofuSentenceRepository tofuSentenceRepository;

    @Inject
    private KuromojiService kuromojiService;

    private String uuid() {
        return SecurityUtils.getUserUuid();
    }

    @Inject
    private CustomPermissionEvaluator CPE;

    @Transactional(readOnly = false)
    public TofuSentenceTranslation saveTranslation(TofuSentenceTranslation translation) {
        log.debug("saveTranslation: {}", translation);
        if (translation.getId() != null) {
            TofuSentenceTranslation tr = dao.find(TofuSentenceTranslation.class, translation.getId());
            CPE.check(tr, Permission.tofu_translation_save);
        }
        translation.setCreatedBy(uuid());
        return dao.save(translation);
    }

    public TofuSentence findById(int id) {
        return coreDB.findUserTofuById(id, uuid());
    }

    public Page<TofuSentence> getTofusByUser(int page, int limit) {
        Page<TofuSentence> result = tofuSentenceRepository.findAll(PaginationUtil.generatePageRequest(page, limit));

        for (TofuSentence p : result) {
            p.setTranslation(coreDB.findUserTofuSentenceTranslation(p.getId(), uuid()));
        }

        return result;
    }

    public List<TofuSentence> byFilter(FilterCompoundSubmitDTO filter) {
        throw new RuntimeException("not implemented");
    }
}
