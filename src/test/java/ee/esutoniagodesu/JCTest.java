package ee.esutoniagodesu;

import ee.esutoniagodesu.util.JCRandom;
import org.junit.Test;

public class JCTest {

    @Test
    public void random8B() {
        String uuid = JCRandom.random13B();
        System.out.println(uuid);
    }
}
