package benchmark.disk;

import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class RandomAccessBenchmark {
    private static final long FILE_SIZE = 1L * 1024 * 1024 * 1024;
    private static final int ACCESS_COUNT = 100_000;
    private static final long DURATION_NS = 5_000_000_000L;
    private static final String FILE_NAME = "random_access_test.dat";

    public static void initializeTestFile() throws IOException {
        File file = new File(FILE_NAME);
        if (file.exists() && file.length() == FILE_SIZE) return;

        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            raf.setLength(FILE_SIZE);
        }
    }

    public static double runFixedSteps(String mode, int bufferSize, boolean write) throws IOException {
        Random rand = new Random();
        byte[] buffer = new byte[bufferSize];
        long totalBytes = 0;

        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, write ? "rw" : "r")) {
            for (int i = 0; i < ACCESS_COUNT; i++) {
                long pos = (Math.abs(rand.nextLong()) % (FILE_SIZE - bufferSize));
                raf.seek(pos);
                if (write) {
                    raf.write(buffer);
                } else {
                    raf.readFully(buffer);
                }
                totalBytes += bufferSize;
            }
        }

        return totalBytes / (1024.0 * 1024.0);
    }

    public static int runFixedTime(String mode, int bufferSize, boolean write) throws IOException {
        Random rand = new Random();
        byte[] buffer = new byte[bufferSize];
        long operations = 0;
        long start = System.nanoTime();

        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, write ? "rw" : "r")) {
            while (System.nanoTime() - start < DURATION_NS) {
                long pos = (Math.abs(rand.nextLong()) % (FILE_SIZE - bufferSize));
                raf.seek(pos);
                if (write) {
                    raf.write(buffer);
                } else {
                    raf.readFully(buffer);
                }
                operations++;
            }
        }

        return (int) operations;
    }

    public static void main(String[] args) throws IOException {
        initializeTestFile();

        int[] bufferSizes = {512, 4 * 1024, 64 * 1024, 1024 * 1024};

        System.out.println("=== Fixed Steps (fs) Write Speed ===");
        for (int bufSize : bufferSizes) {
            long start = System.nanoTime();
            double mb = runFixedSteps("fs", bufSize, true);
            long time = System.nanoTime() - start;
            double mbps = mb / (time / 1e9);
            System.out.printf("Buffer: %d B | Write Speed: %.2f MB/s%n", bufSize, mbps);
        }

        System.out.println("\n=== Fixed Time (ft) Read IOPS ===");
        for (int bufSize : bufferSizes) {
            int ops = runFixedTime("ft", bufSize, false);
            double iops = ops / (DURATION_NS / 1e9);
            System.out.printf("Buffer: %d B | Read IOPS: %.2f%n", bufSize, iops);
        }
    }
}
