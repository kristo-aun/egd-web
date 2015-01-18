package ee.esutoniagodesu.pojo.test.compound;

import com.jc.util.JCRandom;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class BallotEngine<T> implements Serializable {
    private static final long serialVersionUID = 7902097827237481466L;

    private final List<T> ballots = new ArrayList<>();

    public void put(T ballot) {
        put(ballot, 0);
    }

    public void put(T ballot, int repetitions) {
        ballots.add(ballot);
        if (repetitions > 0) {
            for (int p = 0; p < repetitions; p++) {
                ballots.add(ballot);
            }
        }
    }

    public void put(List<T> ballots) {
        this.ballots.addAll(ballots);
    }

    public T draw() {
        Assert.isTrue(ballots.size() > 0);
        int key = JCRandom.between(0, ballots.size() - 1);
        return ballots.remove(key);
    }

    public List<T> draw(int count) {
        List<T> result = new ArrayList<>();
        for (int p = 0; p < count; p++) {
            result.add(draw());
        }
        return result;
    }

    public void clear() {
        ballots.clear();
    }
}
