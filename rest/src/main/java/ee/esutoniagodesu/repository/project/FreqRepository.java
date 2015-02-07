package ee.esutoniagodesu.repository.project;

import ee.esutoniagodesu.domain.freq.table.TofuSentence;
import ee.esutoniagodesu.domain.freq.table.TofuSentenceTranslation;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class FreqRepository extends AbstractProjectRepository {

    public List<TofuSentence> findUserTofus(int from, int to, String createdBy) {
        String sql = "SELECT * from freq.tofu_sentence where id between ?1 and ?2 order by id";
        Query q = em.createNativeQuery(sql, TofuSentence.class);
        q.setParameter(1, from);
        q.setParameter(2, to);

        List<TofuSentence> result  = q.getResultList();

        for (TofuSentence p : result) {
            p.setTranslation(getTofuSentenceTranslation(p.getId(), createdBy));
        }
        return result;
    }

    public TofuSentenceTranslation getTofuSentenceTranslation(int tofuSentenceId, String createdBy) {
        if (tofuSentenceId < 1 || createdBy == null) throw new IllegalArgumentException("getTofuSentenceTranslation");

        try {
            String sql = "SELECT * FROM freq.tofu_sentence_translation WHERE tofu_sentence_id=?1 AND created_by=?2";
            Query q = em.createNativeQuery(sql, TofuSentenceTranslation.class);
            q.setParameter(1, tofuSentenceId);
            q.setParameter(2, createdBy);

            return (TofuSentenceTranslation) q.getSingleResult();
        } catch (NoResultException ignored) {
            return null;
        } catch (Exception e) {
            log.error("msg=" + e.getMessage(), e);
            throw e;
        }
    }
}
