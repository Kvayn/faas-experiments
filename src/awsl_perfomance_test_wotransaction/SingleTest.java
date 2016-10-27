package awsl_perfomance_test_wotransaction;


import awsl_perfomance_test_wotransaction.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SingleTest {
    private int id;
    private int matrixSize;
    private int n = 100;            //number of concurrent functions
    private List<ParallelInvokerThread> threadList = new ArrayList<>();
    private long totalTime = 0;

    public SingleTest(int id, int matrixSize) throws InterruptedException{
        this.id = id;
        this.matrixSize = matrixSize;
    }

    public List<ParallelInvokerThread> getThreadList() {
        return threadList;
    }

    public int getId() {
        return id;
    }

    public long getTotalTime() {
        return totalTime;
    }

    MainParallel1 m = new MainParallel1();
     long test() throws InterruptedException{

        int size = matrixSize;        //the size of matrix

        if (!checkInput(size, n)){
            System.out.println("Wrong input");
            System.exit(-1);
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < n; i++){
            //create input object for function
            awsl_perfomance_test_wotransaction.InputData inputData = new awsl_perfomance_test_wotransaction.InputData(size, n, i);
            //start a certain thread
            ParallelInvokerThread parallelInvokerThread = new ParallelInvokerThread(inputData);
            parallelInvokerThread.start();
            threadList.add(parallelInvokerThread);
            //delay put due to limits of AWS Lambda(1000 requests per second with a burst limit of 2000 rps)
            synchronized (m){
                m.wait(10);
            }
        }
        Iterator<ParallelInvokerThread> threadIterator = threadList.iterator();
        while (threadIterator.hasNext()){
            threadIterator.next().join();
        }
         long result = System.currentTimeMillis() - startTime;
         totalTime = result;
         synchronized (m){
             m.wait(2000);
         }
         return result;
    }
    public static boolean checkInput(int a, int b){
        boolean result = false;
        if (b > a){
            return false;
        }
        if (a%b == 0){
            result = true;
        }
        return result;
    }
    double getAvrgCulcTime(){
        double result = 0;
        Iterator<ParallelInvokerThread> iterator = threadList.iterator();
        while (iterator.hasNext()){
            ParallelInvokerThread thread = iterator.next();
            result += thread.getReceivedData().getComputingTime();
        }
        return result/n;
    }
    double getAvrgTotalTime(){
        double result = 0;
        Iterator<ParallelInvokerThread> iterator = threadList.iterator();
        while (iterator.hasNext()){
            ParallelInvokerThread thread = iterator.next();
            result += thread.getResponseTime();
        }
        return result/n;
    }
    double getAvrgResponceTime(){
        return  getAvrgTotalTime() - getAvrgCulcTime();
    }
    void writeIntoFile(List<ParallelInvokerThread> list, String fileName){
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(fileName, "UTF-8");
            Iterator<ParallelInvokerThread> iterator = list.iterator();
            while (iterator.hasNext()){
                printWriter.print(String.valueOf(iterator.next().getReceivedData().getComputingTime()) + "\t");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }
    }
    void writeRespTimeIntoFile(List<ParallelInvokerThread> list, String fileName){
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(fileName, "UTF-8");
            Iterator<ParallelInvokerThread> iterator = list.iterator();
            while (iterator.hasNext()){
                ParallelInvokerThread parallelInvokerThread = iterator.next();
                printWriter.print(String.valueOf(parallelInvokerThread.getResponseTime() - parallelInvokerThread.getReceivedData().getComputingTime()) + "\t");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }
    }
}
