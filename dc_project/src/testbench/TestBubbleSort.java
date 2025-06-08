package testbench;

import benchmark.BenchmarkSortBubble;
import benchmark.IBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;
import timing.ITimer;
import timing.Timer;

/**
 * Test class for Bubble Sort benchmark.
 *
 * Runs a simple performance test on sorting a random array.
 */
public class TestBubbleSort {
    public static void main(String[] args) {
        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();
        IBenchmark bench = new BenchmarkSortBubble(); // Our actual benchmark class

        // Setup and run
        bench.initialize(10000);    // e.g., create an array with 10,000 elements
        timer.start();
        bench.run();
        log.write("Finished in", timer.stop(), "ns");

        // Close resources
        log.close();
        bench.clean();  // Clean benchmark resources (array memory)
    }
}
