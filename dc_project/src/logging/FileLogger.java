package logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements ILogger {
    private BufferedWriter writer;

    public FileLogger(String filePath) {
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(long value) {
        try {
            writer.write(Long.toString(value));
            writer.newLine();
            writer.flush(); // Ensure it gets written
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush(); // Ensure it gets written
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Object... values) {
        try {
            for (Object value : values) {
                writer.write(value.toString() + " ");
            }
            writer.newLine();
            writer.flush(); // Ensure it gets written
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            if (writer != null)
                writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
