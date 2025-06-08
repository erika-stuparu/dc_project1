package benchmark.cpu;

import benchmark.IBenchmark;

public class CPUFixedPoint implements IBenchmark {
    private int workload = 1_000_000;

    @Override
    public void initialize(Object... params) {
        if (params.length > 0 && params[0] instanceof Integer) {
            workload = (Integer) params[0];
        }
    }

    @Override
    public void warmup() {
        run();
    }

    @Override
    public void run() {
        int[] a = new int[100];
        int x = 1, y = 2, z = 0;
        for (int i = 0; i < workload; i++) {
            a[i % 100] = x + y;
            z = a[i % 100] * 2;
            z = z >> 1;
            z = z & 15;
            z = z | 3;
            z = z ^ 5;
            if (z < 50) {
                z += 1;
            } else {
                z -= 1;
            }
            z = (z % 3) + (z / 2);
        }
    }

    @Override
    public void run(Object... options) {
        run();
    }

    @Override
    public void cancel() {}

    @Override
    public void clean() {}
}
