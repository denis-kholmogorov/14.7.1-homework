import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class equalsThreads
{
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        int proc = Runtime.getRuntime().availableProcessors();
        int maxRegionCode = 9;
        boolean end = false;
        StringBuilder builder = new StringBuilder();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(proc); // Создаем пул потоков в зависимости от количества процессоров на PC

        char letters[] = {'А','У', 'К', 'Е', 'Н', 'Х', 'В', 'Р', 'О', 'С', 'М', 'Т'};

        for(int regionCode = 1; regionCode <= maxRegionCode; regionCode++ ) {
            for (int number = 1; number < 1000; number++) {
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            builder.append(firstLetter);
                            builder.append(padNumber(number));
                            builder.append(secondLetter);
                            builder.append(thirdLetter);
                            builder.append(padRegionCode(regionCode));
                            builder.append("\n");
                        }
                    }
                }
            }
        }
        System.out.println(builder.length() + " Объем StringBuilder");
        Thread.sleep(1000);

        long start = System.currentTimeMillis();
        System.out.println("Начало записи в один файла");
        PrintWriter writer1 = new PrintWriter("res/numbersOneFile.txt"); //добавляем в массив объекты
        writeOneFilefromTwoThreads(builder,writer1, start, executor);
        Thread.sleep(4000);
        System.out.println("Конец записи в файлы");


        System.out.println("Начало записи в два файла");
        long start2 = System.currentTimeMillis();
        PrintWriter writer2 = new PrintWriter("res/numbersTwoFile-1.txt"); //добавляем в массив объекты
        PrintWriter writer3 = new PrintWriter("res/numbersTwoFile-2.txt");
        writeTwoFilefromTwoThreads(builder,writer2, writer3, start2, executor);

    }

    private static void writeOneFilefromTwoThreads (StringBuilder builder, PrintWriter writer, long start,ThreadPoolExecutor executor) throws InterruptedException {
        WriterTwo writerOne = new WriterTwo(builder,writer,start, true ,1);
        WriterTwo writerTwo = new WriterTwo(builder,writer,start, true, 2);
        writerOne.start();
        Thread.sleep(50);
        writerTwo.start();


    }
    private static void writeTwoFilefromTwoThreads (StringBuilder builder, PrintWriter writer2, PrintWriter writer3, long start, ThreadPoolExecutor executor) throws InterruptedException {

        Writer writer = new Writer(builder,writer2,start, true );
        Writer writer1 = new Writer(builder,writer3,start, true);
        writer.start();
        writer1.start();
    }

    private static String padNumber(int number) { // в методе заменил цикл на условие
        String numberStr = Integer.toString(number);
        int a = number/10;

        if (a == 0)
        {
            return "00" + numberStr;
        }
        else if (a < 10 && a > 0)
        {
            return "0" + numberStr;
        }
        else
        {
            return numberStr;
        }
    }
    private static String padRegionCode(int code) { // в методе заменил цикл на условие
        String numberStr = Integer.toString(code);
        int a = code/10;

        if (a == 0)
        {
            return "0" + numberStr;
        }
        else
        {
            return numberStr;
        }
    }

}
