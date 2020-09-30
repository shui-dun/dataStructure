package algorithm;

public class Random {
    private static final long A = 48271;
    private static final long M = 2147483647;
    private long state;

    public Random() {
        state = System.currentTimeMillis();
    }

    public Random(int state) {
        this.state = (long) state;
    }

    public long nextLong() {
        // 经检验，A * System.currentTimeMillis() 公元8020年才会溢出
        return state = (A * state) % M;
    }

    public double nextDouble() {
        return (double) nextLong() / M;
    }
}
