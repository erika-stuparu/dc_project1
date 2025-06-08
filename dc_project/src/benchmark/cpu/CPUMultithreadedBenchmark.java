package benchmark.cpu;

import benchmark.IBenchmark;

public class CPUMultithreadedBenchmark implements IBenchmark {
    private int workload = 100_000_000;
    private int nThreads = 1;

    @Override
    public void initialize(Object... params) {
        this.nThreads = (int) params[0];
    }

    @Override
    public void warmup() {
        run();
    }

    @Override
    public void run() {
        Thread[] threads = new Thread[nThreads];
        int workPerThread = workload / nThreads;

        Runnable task = () -> {
            long dummy = 0;
            for (int i = 0; i < workPerThread; i++) {
                dummy += i ^ (i >> 1);
            }
        };

        for (int i = 0; i < nThreads; i++) {
            threads[i] = new Thread(task);
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ignored) {}
        }
    }

    @Override
    public void run(Object... options) {
        run();
    }

    @Override
    public void cancel() {}

    @Override
    public void clean() {}
}
