import logging.ConsoleLogger;
import logging.FileLogger;
import timing.Timer;

public class MainTestbench {
    public static void main(String[] args) {
        ConsoleLogger consoleLogger = new ConsoleLogger();
        FileLogger fileLogger = new FileLogger("benchmark_log.txt");
        Timer timer = new Timer();

        timer.start();
        // Simulate some workload
        for (int i = 0; i < 1000000; i++);
        long elapsed = timer.stop();

        consoleLogger.write("Elapsed time (console): ", elapsed, "ns");
        fileLogger.write("Elapsed time (file): ", elapsed, "ns");

        consoleLogger.close();
        fileLogger.close();
    }
}
