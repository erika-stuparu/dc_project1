package benchmark.disk;

import java.util.ArrayList;
import java.util.List;

public class HugeMemoryAllocator {
    public static void main(String[] args) {
        List<byte[]> memoryBlocks = new ArrayList<>();
        int blockSize = 1024 * 1024; // 1 MB
        long totalAllocatedMB = 0;

        try {
            while (true) {
                byte[] block = new byte[blockSize];
                memoryBlocks.add(block);

                // Touch the memory to force real allocation
                for (int i = 0; i < block.length; i++) {
                    block[i] = 1;
                }

                totalAllocatedMB++;
                if (totalAllocatedMB % 512 == 0) {
                    System.out.println("Allocated: " + totalAllocatedMB + " MB");
                }
            }
        } catch (OutOfMemoryError e) {
            System.err.println("System ran out of memory!");
            System.err.println("Total allocated before crash: " + totalAllocatedMB + " MB");
        }
    }
}
