package ee.esutoniagodesu.repository.domain.heisig;

import ee.esutoniagodesu.domain.heisig.table.HeisigCoreKw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeisigCoreKwRepository extends JpaRepository<HeisigCoreKw, Integer> {
    Optional<HeisigCoreKw> findOneByKanji(String kanji);
}
