package awsl_perfomance_test_wotransaction;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainParallel1 {

    public static void main(String[] args) throws InterruptedException {
        int k = 10;
        double[] totalTimes = new double[k];
        double[] avgCalcTimes = new double[k];
        double[] avgTotalTimes = new double[k];
        String resultString = "";

        for (int i = 0; i < k; i++){
            SingleTest singleTest = new SingleTest(i, 1000);
            totalTimes[i] = singleTest.test();
            avgCalcTimes[i] = singleTest.getAvrgCulcTime();
            avgTotalTimes[i] = singleTest.getAvrgTotalTime();
            //singleTest.writeIntoFile(singleTest.getThreadList(), "testResult" + i + ".txt");
            //singleTest.writeRespTimeIntoFile(singleTest.getThreadList(), "responseTime" + i + ".txt");
            resultString += createString(singleTest);

        }
        resultString += "*THE AVERAGE CALCULATION TIME OF EACH FUNCTION: " + getAvarage(avgCalcTimes) + " ms;\n";
        resultString += "*THE AVERAGE TOTAL TIME OF EACH FUNCTION: " + getAvarage(avgTotalTimes) + "ms;\n";
        resultString += "*THE AVERAGE TOTAL TIME OF EACH TEST: " + getAvarage(totalTimes) + " ms;\n";
        System.out.println(resultString);


    }
    static double getAvarage(double[] arr){
        double result = 0;
        for (int i = 0; i < arr.length; i++){
            result = result + arr[i];
        }
        return result/arr.length;
    }
    static String createString(SingleTest singleTest){
        String head = "Test no " + singleTest.getId() + ":\n";
        String body = "\t-Average calculation time of each function: " + String.valueOf(singleTest.getAvrgCulcTime()) + " ms;\n" +
                "\t-Average total time(request-calculation-response) of each function: " + String.valueOf(singleTest.getAvrgTotalTime()) + " ms;\n" +
                "\t-The total time of test: " + singleTest.getTotalTime() + " ms;\n\n";
        return head + body;

    }
    static void writeIntoFile(String data, String fileName){
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(fileName, "UTF-8");
            printWriter.print(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }
    }


}
