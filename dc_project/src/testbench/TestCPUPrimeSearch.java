package testbench;

import benchmark.IBenchmark;
import benchmark.cpu.CPUPrimeSearch;
import logging.ConsoleLogger;
import logging.ILogger;
import timing.Timer;

public class TestCPUPrimeSearch {
    public static void main(String[] args) {
        ILogger log = new ConsoleLogger();
        Timer timer = new Timer();
        IBenchmark bench = new CPUPrimeSearch();

        bench.initialize();
        bench.warmup();

        timer.start();
        Thread t = new Thread(() -> bench.run());
        t.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        bench.cancel();
        long elapsed = timer.stop();

        long lastPrime = ((CPUPrimeSearch) bench).getLastPrime();
        log.write("Last prime found:", lastPrime);
        log.write("Runtime (ms):", elapsed / 1_000_000);

        double score = (lastPrime / (elapsed / 1_000_000.0)) * 0.001;
        log.write("Benchmark Score:", score);

        log.close();
    }
}
