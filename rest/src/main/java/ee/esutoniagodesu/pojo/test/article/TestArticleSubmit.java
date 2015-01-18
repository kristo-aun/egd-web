package ee.esutoniagodesu.pojo.test.article;

import ee.esutoniagodesu.pojo.test.compound.EDictionary;
import ee.esutoniagodesu.pojo.test.compound.EFilterType;
import ee.esutoniagodesu.pojo.test.compound.EKanjiIntervalType;
import ee.esutoniagodesu.pojo.test.compound.EOrderByType;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Testi koostamise vormi parameetrid
 * <p/>
 * Sõnastik, raadionupp
 * JMDict
 * JMDict + sagedusindeks, rippmenüü
 * ngres-base-aggr
 * JMDict + ILO Jaapani-Eesti
 * Core10k
 * kaks rippmenüüd, vahemik 1-10,
 * *Jinmei,
 * kaks numbrivälja, 1 - 772
 * <p/>
 * Sõnade arv, numbriväli
 * Kanjide arv sõnas, 2 rippmenüüd, vahemik 1 - n
 * Linnuke "näita sõnaliike ja erialamärgendeid"
 * <p/>
 * Filter (va. Core10k & Jinmei)
 * <p/>
 * filtri liik [grade, jlpt, jouyou, jinmei, heisig]
 * indeksi vahemik vastavalt liigile
 * grade, kaks rippmenüüd, vahemik 1 - 9
 * jlpt, kaks rippmenüüd, vahemik 1 - 4
 * jouyou, kaks rippmenüüd, vahemik 1 - 7
 * <p/>
 * heisig4, raadionupp
 * lesson 1 - 62
 * frame 1 - 2042
 * <p/>
 * heisig6, raadionupp
 * lesson 1-56
 * frame 1 - 2200
 * <p/>
 * order by
 * random
 * filter id
 * raadionupp asc/desc
 * <p/>
 * Linnuke "lisa joonte arv"
 * Linnuke "lisa radikaalide vihje"
 * kui radikaale on rohkem kui 1, lisab 10 suvalises järjekorras radikaali
 */
public final class TestArticleSubmit implements Serializable {
    private static final long serialVersionUID = 6845522545849202408L;

    public int filterType;//millist kanji indekstit kasutada
    public String kanjiIntervalType = EKanjiIntervalType.index.name();//kas loend on piiritletud indeksi või taseme intervalliga
    public final int[] kanjiInterval = new int[]{0, 0};//indeksi või taseme intervall
    public int dictionary;
    public boolean notesVisible;

    public boolean noEnIfHasEt;
    public int generateCount;
    public final int[] compLengthInterval = new int[]{1, 0};
    public int orderByType;
    public boolean strokeCountHintVisible;

    public boolean radicalHintVisible;
    public boolean generatePdf;

    public void setKanjiInterval(int from, int to) {
        kanjiInterval[0] = from;
        kanjiInterval[1] = to;
    }

    public void setCompLengthInterval(int from, int to) {
        compLengthInterval[0] = from;
        compLengthInterval[1] = to;
    }

    public EDictionary getEDictionary() {
        return EDictionary.findById(dictionary);
    }

    public EFilterType getEFilterType() {
        return EFilterType.findById(filterType);
    }

    public EKanjiIntervalType getEKanjiIntervalType() {
        return EKanjiIntervalType.findByName(kanjiIntervalType);
    }

    public EOrderByType getEOrderByType() {
        return EOrderByType.findById(orderByType);
    }

    public String toString() {
        return "TestCompoundSubmit{" +
            "filterType=" + getEFilterType() +
            ", kanjiIntervalType=" + getEKanjiIntervalType() +
            ", kanjiInterval=" + Arrays.toString(kanjiInterval) +
            ", dictionary=" + getEDictionary() +
            ", notesVisible=" + notesVisible +
            ", noEnIfHasEt=" + noEnIfHasEt +
            ", generateCount=" + generateCount +
            ", compLengthInterval=" + Arrays.toString(compLengthInterval) +
            ", orderByType=" + getEOrderByType() +
            ", strokeCountHintVisible=" + strokeCountHintVisible +
            ", radicalHintVisible=" + radicalHintVisible +
            ", generatePdf=" + generatePdf +
            '}';
    }
}
