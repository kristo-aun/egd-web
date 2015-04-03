package ee.esutoniagodesu.pojo.entity;

import ee.esutoniagodesu.domain.estwn.table.Example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Eesti-Jaapani sõnastiku transpordiobjekt
 */
public final class EstJap implements Serializable {

    private static final long serialVersionUID = -791923331047978723L;

    public String gtxt;//gloss txt
    public String[] gpos;//sõnaliigid ee

    public List<Example> sentences;

    public final List<Sens> sens = new ArrayList<>();//tähendused

    public static class Sens implements Serializable {
        private static final long serialVersionUID = -6863104823818180749L;

        public int entr;//JMDict.entr
        public String[] kanj;
        public String[] rdng;

        public Sens(String[] kanj, String[] rdng) {
            this.kanj = kanj;
            this.rdng = rdng;
        }
    }
}
