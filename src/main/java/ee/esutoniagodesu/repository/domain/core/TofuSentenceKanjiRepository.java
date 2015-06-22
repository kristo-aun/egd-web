package ee.esutoniagodesu.repository.domain.core;

import ee.esutoniagodesu.domain.core.table.TofuSentenceKanji;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TofuSentenceKanjiRepository extends JpaRepository<TofuSentenceKanji, Integer> {
    Optional<TofuSentenceKanji> findOneByKanji(String kanji);
}
