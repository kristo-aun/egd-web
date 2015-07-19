package ee.esutoniagodesu.pojo.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Transpordiobjektid.
 */
public final class JapEst implements Serializable {
    private static final long serialVersionUID = -670456271268785686L;

    public int entr;
    public String[] kanj;
    public String[] rdng;
    public final List<Sens> sens = new ArrayList<>();

    public static class Sens implements Serializable {
        private static final long serialVersionUID = -488898344843474085L;
        public String gloss;

        public List<String> sentences;

        public Sens(String gloss) {
            this.gloss = gloss;
        }
    }

    public String toString() {
        return "JMEntry{" +
            "entr=" + entr +
            ", kanj=" + Arrays.toString(kanj) +
            ", rdng=" + Arrays.toString(rdng) +
            ", sens=" + sens +
            '}';
    }
}
