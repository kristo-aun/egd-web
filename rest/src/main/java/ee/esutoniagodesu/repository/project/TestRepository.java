package ee.esutoniagodesu.repository.project;

import ee.esutoniagodesu.domain.test.table.Article;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class TestRepository extends AbstractProjectRepository {

    public List<Article> findUserArticles(String login) {
        StringBuilder msg = new StringBuilder("findUserArticles: login=" + login);

        try {
            String sql = "SELECT * FROM test.article where created_by=?1 or shared = true ORDER BY id desc";
            Query q = em.createNativeQuery(sql, Article.class);
            q.setParameter(1, login);

            List<Article> result  = q.getResultList();
            log.debug(msg.append(", result.size=").append(result.size()).toString());
            return result;
        } catch (Exception e) {
            log.error(msg.append(", msg=").append(e.getMessage()).toString(), e);
            throw e;
        }
    }
}