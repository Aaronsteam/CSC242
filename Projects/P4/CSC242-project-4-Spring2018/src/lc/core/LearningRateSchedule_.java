package lc.core;

public class LearningRateSchedule_ implements LearningRateSchedule {

    public double alpha(int t) {
        // not perfect but better than fixed rate
        return 1000/(1000 + t);
    }
}
