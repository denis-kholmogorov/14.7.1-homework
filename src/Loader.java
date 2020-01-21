
/**
Время выполнения итерации 100 регионов в два файла.
 46403 ms pool-1-thread-2
 46455 ms pool-1-thread-1
*/

import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Loader
{
    public static void main(String[] args) throws Exception
    {
        long start = System.currentTimeMillis();
        int proc = Runtime.getRuntime().availableProcessors();
        int maxRegionCode = 100;
        boolean end = false;
        StringBuilder builder;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(proc); // Создаем пул потоков в зависимости от количества процессоров на PC

        PrintWriter writers1 = new PrintWriter("res/numbersOne1.txt"); //добавляем в массив объекты
        PrintWriter writers2 = new PrintWriter("res/numbersOne2.txt"); //добавляем в массив объекты


        char letters[] = {'А','У', 'К', 'Е', 'Н', 'Х', 'В', 'Р', 'О', 'С', 'М', 'Т'};

        for(int regionCode = 1; regionCode <= maxRegionCode; regionCode++ )
        {
            builder = new StringBuilder(); //содание билдера
          if(regionCode == maxRegionCode) end = true;

            for (int number = 1; number < 1000; number++)
            {
                for (char firstLetter : letters)
                {
                    for (char secondLetter : letters)
                    {
                        for (char thirdLetter : letters)
                        {
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


            executor.execute(new Writer(builder, writers1, start, end));
            executor.execute(new Writer(builder, writers2, start, end));
            /* Использование потоков для задачи записи в файлы.
             * Использование количества потоков большего чем ядер у процессора не приводит к ускорению.
             * Т.к учащается переключения ядра между потоками
             * Программа работает быстрее если файлов не существует и они создаются заново
             *
             * Так же попробывал накапливать builder и вызывать его только про прохождении 2, 3, 4 циклов regionCode
             * но в скорости не выйграл и очень много памяти начинает кушать
             * на двух ядерном pc получаются при 10 регионах
             * 7608 ms pool-1-thread-1
             * 7620 ms pool-1-thread-2
             * Быстрее не получается
             * */

        }
        executor.shutdown();
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

