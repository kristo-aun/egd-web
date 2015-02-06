package ee.esutoniagodesu.repository.project;

import ee.esutoniagodesu.domain.freq.table.TofuSentence;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FreqRepository extends AbstractProjectRepository {

    public List<TofuSentence> findUserTofus(String login) {
        return null;
    }
}
