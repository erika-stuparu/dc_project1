package benchmark.cpu;

import benchmark.IBenchmark;

public class CPUPrimeSearch implements IBenchmark {
    private long lastPrime = 2;
    private boolean cancelled = false;

    @Override
    public void initialize(Object... params) {
        lastPrime = 2;
        cancelled = false;
    }

    @Override
    public void warmup() {
        for (long i = 2; i < 10000; i++) {
            isPrime(i);
        }
    }

    @Override
    public void run() {
        try {
            for (long i = 2; ; i++) {
                if (cancelled) break;
                if (isPrime(i)) {
                    lastPrime = i;
                }
            }
        } catch (OutOfMemoryError | ArithmeticException e) {
            System.out.println("Crashed at prime: " + lastPrime);
        }
    }

    @Override
    public void run(Object... options) {
        run();
    }

    @Override
    public void cancel() {
        cancelled = true;
    }

    @Override
    public void clean() {}

    private boolean isPrime(long n) {
        if (n < 2) return false;
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public long getLastPrime() {
        return lastPrime;
    }
}
