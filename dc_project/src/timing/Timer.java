package timing;

public class Timer implements ITimer {
    private long startTime;
    private long totalTime;
    private boolean running;

    @Override
    public void start() {
        totalTime = 0;
        startTime = System.nanoTime();
        running = true;
    }

    @Override
    public long stop() {
        if (running) {
            totalTime += System.nanoTime() - startTime;
            running = false;
        }
        return totalTime;
    }

    @Override
    public void resume() {
        if (!running) {
            startTime = System.nanoTime();
            running = true;
        }
    }

    @Override
    public long pause() {
        if (running) {
            totalTime += System.nanoTime() - startTime;
            running = false;
        }
        return totalTime;
    }
}
