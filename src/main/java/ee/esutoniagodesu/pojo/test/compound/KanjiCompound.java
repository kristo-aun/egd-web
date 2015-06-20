package ee.esutoniagodesu.pojo.test.compound;

import ee.esutoniagodesu.util.lang.alphab.JCCharacter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class KanjiCompound implements ICountKanjis, Serializable {
    private static final long serialVersionUID = 759682473968816526L;

    public String et;
    public String en;
    public String notes;//pos, kinf

    public String answer;
    public String reading;
    public final List<Calligraphy> signs = new ArrayList<>();

    public String heisigCoreKw;
    public boolean heisigEquals;

    public int getCountKanjis() {
        int result = 0;
        for (Calligraphy p : signs) {
            if (p.kanji) result++;
        }
        return result;
    }

    public static class Calligraphy implements Serializable {
        private static final long serialVersionUID = 1504016250838249205L;

        public Calligraphy(char sign) {
            this.sign = sign;
            kanji = JCCharacter.isKanji(sign);
        }

        public final char sign;
        public boolean kanji;
        public Integer strokeCountHint;
        public String radicalHint;

    }
}
