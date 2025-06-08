package benchmark.disk;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class DiskWriteBenchmark {
    private static final int DEFAULT_REPEATS = 5;

    public static void runFixedSizeVariant(long fileSizeBytes, int[] bufferSizes) {
        System.out.println("Variant: Fixed file size = " + (fileSizeBytes / (1024 * 1024)) + "MB");
        for (int bufferSize : bufferSizes) {
            double totalSpeed = 0;
            for (int i = 0; i < DEFAULT_REPEATS; i++) {
                double speed = writeTest("fs_test_" + bufferSize + "B_run" + i + ".dat", fileSizeBytes, bufferSize);
                totalSpeed += speed;
                System.out.printf("Buffer: %dB | Run %d | Speed: %.2f MB/s%n", bufferSize, i + 1, speed);
            }
            System.out.printf(">> Avg speed for buffer %dB: %.2f MB/s%n\n", bufferSize, totalSpeed / DEFAULT_REPEATS);
        }
    }

    public static void runFixedBufferVariant(int bufferSizeBytes, long[] fileSizes) {
        System.out.println("Variant: Fixed buffer size = " + (bufferSizeBytes / 1024) + "KB");
        for (long fileSize : fileSizes) {
            double totalSpeed = 0;
            for (int i = 0; i < DEFAULT_REPEATS; i++) {
                double speed = writeTest("fb_test_" + fileSize + "B_run" + i + ".dat", fileSize, bufferSizeBytes);
                totalSpeed += speed;
                System.out.printf("File: %dB | Run %d | Speed: %.2f MB/s%n", fileSize, i + 1, speed);
            }
            System.out.printf(">> Avg speed for file %dB: %.2f MB/s%n\n", fileSize, totalSpeed / DEFAULT_REPEATS);
        }
    }

    private static double writeTest(String filename, long fileSizeBytes, int bufferSize) {
        byte[] buffer = new byte[bufferSize];
        new Random().nextBytes(buffer);

        long bytesWritten = 0;
        long start = System.nanoTime();
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            while (bytesWritten < fileSizeBytes) {
                int toWrite = (int) Math.min(bufferSize, fileSizeBytes - bytesWritten);
                fos.write(buffer, 0, toWrite);
                bytesWritten += toWrite;
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
        long end = System.nanoTime();
        double timeSec = (end - start) / 1_000_000_000.0;
        return (fileSizeBytes / 1024.0 / 1024.0) / timeSec; // MB/s
    }

    public static void main(String[] args) {
        int[] bufferSizes = {
                1 * 1024, 4 * 1024, 16 * 1024, 64 * 1024,
                256 * 1024, 1 * 1024 * 1024, 4 * 1024 * 1024,
                16 * 1024 * 1024, 64 * 1024 * 1024
        };
        runFixedSizeVariant(512L * 1024 * 1024, bufferSizes);

        long[] fileSizes = {
                1L * 1024 * 1024,
                10L * 1024 * 1024,
                100L * 1024 * 1024,
                1024L * 1024 * 1024
        };
        runFixedBufferVariant(2 * 1024, fileSizes);
    }
}
