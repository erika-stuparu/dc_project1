package testbench;

import benchmark.IBenchmark;
import benchmark.cpu.CPUFixedPoint;
import logging.ConsoleLogger;
import logging.ILogger;
import timing.Timer;

public class TestCPUFixedPoint {
    public static void main(String[] args) {
        IBenchmark benchmark = new CPUFixedPoint();
        Timer timer = new Timer();
        ILogger log = new ConsoleLogger();

        int workload = 100_000_000;
        int opsPerIter = 10; // estimated: 1 op per statement, div/mod count as 3

        benchmark.initialize(workload);
        benchmark.warmup();

        timer.start();
        benchmark.run();
        long timeNs = timer.stop();

        double timeSec = timeNs / 1_000_000_000.0;
        double mops = (opsPerIter * workload) / timeSec / 1_000_000;

        log.write("Time:", timeSec, "sec");
        log.write("MOPS:", mops);

        benchmark.clean();
        log.close();
    }
}
