import java.io.PrintWriter;

public class WriterTwo extends Thread {

    private final StringBuilder builder;
    private final PrintWriter writer;
    private long start;
    boolean end;
    int numThread;

    public WriterTwo(StringBuilder builder, PrintWriter writer, long start, boolean end, int numThread) {
        this.builder = builder;
        this.start = start;
        this.writer = writer;
        this.end = end;
        this.numThread = numThread;

    }

    @Override
    public void run() {


        int builderLenth = builder.length();
        if (numThread == 1)
        {
            writer.write(builder.substring(0, builderLenth / 2));
        }
        else
        {
            writer.write(builder.substring((builderLenth / 2) + 1, builderLenth));
            WriteTwoThreadsInOneFile.allWritten = true;
        }

        System.out.println((System.currentTimeMillis() - start) + " ms " + Thread.currentThread().getName());

        }
    }

