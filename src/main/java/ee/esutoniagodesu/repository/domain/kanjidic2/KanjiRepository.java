package ee.esutoniagodesu.repository.domain.kanjidic2;

import ee.esutoniagodesu.domain.kanjidic2.table.Kanji;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KanjiRepository extends JpaRepository<Kanji, Integer> {
    Optional<Kanji> findByLiteral(String literal);
}
