package benchmark;

/**
 * A simple Bubble Sort benchmark for CPU testing.
 */
public class BenchmarkSortBubble implements IBenchmark {
    private int[] array;
    private volatile boolean running = true;

    @Override
    public void initialize(Object... params) {
        if (params.length > 0 && params[0] instanceof Integer) {
            int size = (Integer) params[0];
            array = new int[size];
            for (int i = 0; i < size; i++) {
                array[i] = (int)(Math.random() * size);
            }
        }
    }

    @Override
    public void run() {
        if (array == null) {
            System.out.println("Benchmark not initialized!");
            return;
        }
        bubbleSort(array);
    }

    @Override
    public void run(Object... params) {
        run();
    }

    @Override
    public void warmup(){}

    @Override
    public void clean() {
        array = null;
    }

    @Override
    public void cancel() {
        running = false;
    }

    private void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (!running) return; // Stop if cancel() was called
                if (arr[i - 1] > arr[i]) {
                    int temp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = temp;
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
    }
}
