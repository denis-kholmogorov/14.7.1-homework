/**
 * Время выполнения записей из двух потоков в один файл при итерации 100 регионов
 * 33257 ms pool-1-thread-1
 * 33261 ms pool-1-thread-2
 */


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class WriteTwoThreadsInOneFile
{
    public static boolean allWritten = false;

    public static void main(String[] args) throws FileNotFoundException {
        long start = System.currentTimeMillis();
        int proc = Runtime.getRuntime().availableProcessors();
        int maxRegionCode = 100;
        boolean end = false;
        StringBuilder builder = new StringBuilder();
        PrintWriter writer = new PrintWriter("res/numbersTwoThreads.txt"); //добавляем в массив объекты
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(proc); // Создаем пул потоков в зависимости от количества процессоров на PC

        char letters[] = {'А','У', 'К', 'Е', 'Н', 'Х', 'В', 'Р', 'О', 'С', 'М', 'Т'};

        for(int regionCode = 1; regionCode <= maxRegionCode; regionCode++ ) {

            if (regionCode == maxRegionCode) end = true;
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
            try {
                writeToFile(builder, writer, start, end, executor);
                if(end && allWritten){
                    writer.flush();
                    writer.close();
                    executor.shutdown();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            builder = new StringBuilder(); //содание билдера
        }
    }

    private static void writeToFile (StringBuilder builder, PrintWriter writer, long start, boolean end, ThreadPoolExecutor executor) throws InterruptedException {
        WriterTwo writerOne = new WriterTwo(builder,writer,start,end, 1);
        WriterTwo writerTwo = new WriterTwo(builder,writer,start,end, 2);
        executor.execute(writerOne);
        writerOne.join();
        executor.execute(writerTwo);
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
