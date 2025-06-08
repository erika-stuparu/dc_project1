package testbench;

import benchmark.IBenchmark;
import benchmark.cpu.CPUMultithreadedBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;
import timing.Timer;

public class TestCPUMultithreading {
    public static void main(String[] args) {
        ILogger log = new ConsoleLogger();
        Timer timer = new Timer();

        int[] threadCounts = {1, 2, 4, 8, 16, 32, 64};
        int workload = 100_000_000;

        log.write("Threads\tTime(s)\tScore");

        for (int n : threadCounts) {
            IBenchmark bench = new CPUMultithreadedBenchmark();
            bench.initialize(n);
            bench.warmup();

            timer.start();
            bench.run();
            long timeNs = timer.stop();

            double timeSec = timeNs / 1_000_000_000.0;
            double score = workload / (timeSec * n);

            log.write(n + "\t" + timeSec + "\t" + score);
            bench.clean();
        }

        log.close();
    }
}
