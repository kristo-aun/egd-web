package ee.esutoniagodesu.web.rest.dto;

import java.io.Serializable;

public class VocabularyDTO implements Serializable {

    private static final long serialVersionUID = -1654108900948411891L;

    public String txt;
    public String rdng;
    public String romaji;
    public String pos;
    public String gloss;

    public String toString() {
        return "VocabularyDTO{" +
            "txt='" + txt + '\'' +
            ", rdng='" + rdng + '\'' +
            ", romaji='" + romaji + '\'' +
            ", pos='" + pos + '\'' +
            ", gloss='" + gloss + '\'' +
            '}';
    }
}
