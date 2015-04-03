package ee.esutoniagodesu.domain.publik.view;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

public final class VStats implements Serializable {

    private static final long serialVersionUID = -4273357621397632151L;
    @JsonIgnore
    public final Date created = new Date();

    public Integer countJpEntry;
    public Integer countTranslatedEntries;
    public Integer countEtTranslations;
    public Integer countSentencePairs;
    public Integer countEstwnExamples;
    public Integer countOriginJatik;
    public Integer countOriginIlo;
    public Integer countOriginEgd;
    public Integer countJpSearchWords;
    public Integer countJpSearchWithResults;
    public Integer countEtSearchWords;
    public Integer countEtSearchWithResults;
    public Integer countKanjiInDb;
    public Integer countKanjisDescribedWithHeisig;
    public Integer countStrokeDiagrams;

    //not in db
    public String jmdictVersion;

    public String toString() {
        return "VStats{" +
            "countJpEntry=" + countJpEntry +
            ", countTranslatedEntries=" + countTranslatedEntries +
            ", countEtTranslations=" + countEtTranslations +
            ", countSentencePairs=" + countSentencePairs +
            ", countEstwnExamples=" + countEstwnExamples +
            ", countOriginJatik=" + countOriginJatik +
            ", countOriginIlo=" + countOriginIlo +
            ", countOriginEgd=" + countOriginEgd +
            ", countJpSearchWords=" + countJpSearchWords +
            ", countJpSearchWithResults=" + countJpSearchWithResults +
            ", countEtSearchWords=" + countEtSearchWords +
            ", countEtSearchWithResults=" + countEtSearchWithResults +
            ", countKanjiInDb=" + countKanjiInDb +
            ", countKanjisDescribedWithHeisig=" + countKanjisDescribedWithHeisig +
            ", countStrokeDiagrams=" + countStrokeDiagrams +
            '}';
    }
}
