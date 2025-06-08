package testbench;

import benchmark.IBenchmark;
import benchmark.cpu.CPUDigitsOfPi;
import logging.ConsoleLogger;
import logging.FileLogger;
import logging.ILogger;
import timing.Timer;

public class TestCPUDigitsOfPi {
    public static void main(String[] args) {
        try {
            System.out.println("Starting Pi benchmark...");

            IBenchmark benchmark = new CPUDigitsOfPi();
            Timer timer = new Timer();
            ILogger logFile = new FileLogger("pi_runtimes.csv");
            ILogger logConsole = new ConsoleLogger();

            logFile.write("Digits,Time(ms)");

            for (int n = 50; n <= 100000; n *= 2) {
                benchmark.initialize(n);
                benchmark.warmup();

                timer.start();
                benchmark.run();
                long timeMs = timer.stop() / 1_000_000; // convert from ns to ms

                logFile.write(n + "," + timeMs);
                logConsole.write("Digits:", n, "Time:", timeMs, "ms");
            }

            logFile.close();
            benchmark.clean();
        } catch (Exception e) {
            System.out.println("An error occurred:");
            e.printStackTrace();
        }
    }
}
