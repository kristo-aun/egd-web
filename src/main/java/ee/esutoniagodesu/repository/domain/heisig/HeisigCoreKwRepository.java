package ee.esutoniagodesu.repository.domain.heisig;

import ee.esutoniagodesu.domain.heisig.table.HeisigCoreKw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HeisigCoreKwRepository extends JpaRepository<HeisigCoreKw, Integer> {
    Optional<HeisigCoreKw> findOneByKanji(String kanji);

    @Query("SELECT a FROM HeisigCoreKw a order by a.id")
    List<HeisigCoreKw> findAll();

    @Query("SELECT a FROM HeisigCoreKw a WHERE a.keywordEnAudio is not null order by a.id")
    List<HeisigCoreKw> findByKeywordAudioNotNull();

    @Query("SELECT a FROM HeisigCoreKw a WHERE a.id between ?1 and ?2 and a.keywordEnAudio is null order by a.id")
    List<HeisigCoreKw> findByKeywordAudioIsNull(int from, int to);
}
