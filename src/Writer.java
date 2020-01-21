import java.io.PrintWriter;

public class Writer extends Thread {

    private final StringBuilder builder;
    private final PrintWriter writer;
    private long start;
    boolean end;

    public Writer(StringBuilder builder, PrintWriter writer, long start, boolean end) {
        this.builder = builder;
        this.start = start;
        this.writer = writer;
        this.end = end;

    }

    @Override
    public void run() {
        writer.write(builder.toString());
        if(end) {
            writer.flush();
            writer.close(); // закрываю writer после записи всех данных по флагу end
        }
        System.out.println((System.currentTimeMillis() - start) + " ms " + Thread.currentThread().getName());
        System.out.println("Конец записи в один из двух файлов");
    }
}
