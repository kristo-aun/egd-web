package ee.esutoniagodesu.service;

import ee.esutoniagodesu.domain.estwn.table.Example;
import ee.esutoniagodesu.domain.estwn.table.Variant;
import ee.esutoniagodesu.domain.estwn.table.WordMeaning;
import ee.esutoniagodesu.util.persistence.ProjectDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;

/**
 * Eesti WordNeti töötlemine. Baasiskeem estwn
 */
@Service
@Transactional
public class EstwnService {

    @Inject
    private ProjectDAO dao;

    public void persist(WordMeaning meaning) {
        Assert.notNull(meaning);
        dao.save(meaning);
        meaning.getVariants().forEach(this::persist);
    }

    public void persist(Variant variant) {
        Assert.notNull(variant);
        dao.save(variant);
        variant.getExamples().forEach(this::persist);
    }

    public void persist(Example example) {
        Assert.notNull(example);
        dao.save(example);
    }

    public void commit() {
        dao.flush();
    }
}
