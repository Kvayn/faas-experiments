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
        int k = 1;
        int matrixSize = 2000;
        double[] totalTimes = new double[k];
        double[] avgCalcTimes = new double[k];
        double[] avgTotalTimes = new double[k];
        double[] avgResponseTime = new double[k];
        String resultString = "";

        for (int i = 0; i < k; i++){
            SingleTest singleTest = new SingleTest(i, matrixSize);
            totalTimes[i] = singleTest.test();
            avgCalcTimes[i] = singleTest.getAvrgCulcTime();
            avgTotalTimes[i] = singleTest.getAvrgTotalTime();
            avgResponseTime[i] = singleTest.getAvrgResponceTime();
            //singleTest.writeIntoFile(singleTest.getThreadList(), "testResult" + i + ".txt");
            //singleTest.writeRespTimeIntoFile(singleTest.getThreadList(), "responseTime" + i + ".txt");
            resultString += createString(singleTest);

        }
        resultString += "*THE AVERAGE CALCULATION TIME OF EACH FUNCTION: " + getAvarage(avgCalcTimes) + " ms;\n";
        resultString += "*THE AVERAGE TOTAL TIME OF EACH FUNCTION: " + getAvarage(avgTotalTimes) + " ms;\n";
        resultString += "*THE AVERAGE RESPONSE TIME OF EACH FUNCTION: " + getAvarage(avgResponseTime) + " ms;\n";
        resultString += "*THE AVERAGE RESPONSE WITH OUT FIRST TEST TIME OF EACH FUNCTION: " +
                getAvarageWithOutFirst(avgResponseTime) + " ms;\n";
        resultString += "*THE AVERAGE TOTAL TIME OF EACH TEST: " + getAvarage(totalTimes) + " ms;\n";
        resultString += getDataForPlot(avgCalcTimes, avgResponseTime, avgTotalTimes, totalTimes);
        System.out.println(resultString);
        String fileName = "" + k + " iterations test; the matrix size: " + matrixSize;
        writeIntoFile(resultString, fileName);


    }
    static String getDataForPlot(double[] avgCalcTimes, double[] avgResponseTimes, double[] avgCalcRespTimes, double[] totalTestTimes){
        String result = "\n---The data for plots---\n";
        result += "Average calculation times of each function per test: \n";
        for (int i = 0; i < avgCalcTimes.length; i++){
            result += round(avgCalcRespTimes[i], 2) + "\t";
        }
        result += "\nAverage request - response times of each function per test: \n";
        for (int i = 0; i < avgResponseTimes.length; i++){
            result += round(avgResponseTimes[i], 2) + "\t";
        }
        result += "\nAverage calc and response times of each function per test: \n";
        for (int i = 0; i < avgCalcRespTimes.length; i++){
            result += round(avgCalcRespTimes[i], 2) + "\t";
        }
        result += "\nTotal times of each test: \n";
        for (int i = 0; i < totalTestTimes.length; i++){
            result += round(totalTestTimes[i], 2) + "\t";
        }
        return  result;
    }
    static double getAvarage(double[] arr){
        double result = 0;
        for (int i = 0; i < arr.length; i++){
            result = result + arr[i];
        }
        return result/arr.length;
    }static double getAvarageWithOutFirst(double[] arr){
        double result = 0;
        for (int i = 1; i < arr.length; i++){
            result = result + arr[i];
        }
        return result/(arr.length-1);
    }

    static String createString(SingleTest singleTest){
        String head = "Test no " + singleTest.getId() + ":\n";
        String body = "\t-Average calculation time of each function: " + String.valueOf(singleTest.getAvrgCulcTime()) + " ms;\n" +
                "\t-Average total time(request-calculation-response) of each function: "
                + String.valueOf(singleTest.getAvrgTotalTime()) + " ms;\n" +
                "\t-Average request-response time: " +
                (String.valueOf(singleTest.getAvrgResponceTime())) + " ms;\n" +
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
    static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


}
