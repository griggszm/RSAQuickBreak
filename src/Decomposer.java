import java.util.ArrayList;
import java.util.List;

public class Decomposer {

    private long p;
    private long q;
    private long millis;
    private final int threadsToUse = 3;

    public long getP() {
        return p;
    }

    public long getQ() {
        return q;
    }

    public long getMillis() {
        return millis;
    }

    public void setP(long p) {
        this.p = p;
    }
    public void setQ(long q) {
        this.q = q;
    }

    public void done() {
        millis = System.currentTimeMillis() - millis;
    }

    /**
     * The hard part - decomposes a number n into p and q.
     *
     * @param n The product of two primes
     * Sets variables p anc q which can then be retrieved.
     */
    public void decompose(long n) {
        List<DecomposingThread> running = new ArrayList<>();
        int threadsLeft = threadsToUse;
        millis = System.currentTimeMillis();
        if(threadsLeft > 0) {
            running.add(new DecomposingThread(n, 2, 1, running, this));
            threadsLeft--;
        }
        if(threadsLeft > 0) {
            running.add(new DecomposingThread(n, (long)(Math.sqrt(n)), 1, running, this));
            threadsLeft--;
        }
        if(threadsLeft > 0) {
            running.add(new DecomposingThread(n, (long)(Math.sqrt(n)), -1, running, this));
            threadsLeft--;
        }
        if(threadsLeft > 0) {
            running.add(new DecomposingThread(n, n, -1, running, this));
            threadsLeft--;
        }
        for(Thread t : running) {
            t.run();
        }
    }

    private class DecomposingThread extends Thread {

        private long n;
        private long startPoint;
        private int increment;
        private Decomposer saveTo;
        private boolean ended = false;
        private List<DecomposingThread> running;

        @Override
        public void run() {
            long current = startPoint;
            while (current < n && current > 1 && ended == false) {
                if(n % current == 0) {
                    System.out.println("P: " + current);
                    System.out.println("Q: " + n/current);
                    saveTo.setP(current);
                    saveTo.setQ(n/current);
                    saveTo.done();
                    for(DecomposingThread t : running) {
                        t.kill();
                    }
                }
                current += increment;

            }
        }

        public void kill() {
            ended = true;
        }

        public DecomposingThread(long n, long startPoint, int increment, List<DecomposingThread> running, Decomposer saveTo) {
            this.startPoint = startPoint;
            this.running = running;
            this.n = n;
            this.increment = increment;
            this.saveTo = saveTo;
        }
    }
}
