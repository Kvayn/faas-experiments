package awsl_performance_test;


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
    private int[][] resultMatrix;

    public SingleTest(int id, int matrixSize) throws InterruptedException{
        this.id = id;
        this.matrixSize = matrixSize;
        resultMatrix = new int[matrixSize][matrixSize];
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

    MainParallel m = new MainParallel();
     long test() throws InterruptedException{

        int size = matrixSize;        //the size of matrix

        if (!checkInput(size, n)){
            System.out.println("Wrong input");
            System.exit(-1);
        }
         int[][] M1 = createMatrix(size);
         int[][] M2 = createMatrix(size);
         int[][] C = new int[size][size];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < n; i++){
            //create input object for function
            InputData inputData = new InputData(M1, M2, size, n, i);
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
         //C = addMatrix(threadList);
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
            result += thread.getReceivedData().getCalcTime();
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
                printWriter.print(String.valueOf(iterator.next().getReceivedData().getCalcTime()) + "\t");
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
                printWriter.print(String.valueOf(parallelInvokerThread.getResponseTime() -
                        parallelInvokerThread.getReceivedData().getCalcTime()) + "\t");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }
    }
    static int[][] createMatrix(int size){
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                result[i][j] = 2;
            }
        }
        return result;
    }
    private int[][]  addMatrix(List<ParallelInvokerThread> listThreads){
        double rowsToCount = matrixSize/n;
        int[][] result = new int[matrixSize][matrixSize];
        for (ParallelInvokerThread thread :
                listThreads) {
            int[][] resultMatrix = thread.getReceivedData().getMatrixC();
            double startRow = rowsToCount * thread.getReceivedData().getId();
            double endRow = startRow + rowsToCount;
            for (int i = (int)startRow; i < (int)endRow; i++){
                for (int j = 0; j < resultMatrix.length; j++){
                    result[i][j] =  result[i][j] + resultMatrix[i][j];
                }
            }
        }
        return  result;
    }
}
