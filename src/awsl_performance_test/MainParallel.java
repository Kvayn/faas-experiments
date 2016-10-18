package awsl_performance_test;

import static awsl_perfomance_test_wotransaction.SingleTest.checkInput;

public class MainParallel {
    public static void main(String[] args) throws InterruptedException {
        int size = 1000;
        int n = 100;
        if (!checkInput(size, n)){
            System.out.println("Wrong input");
            System.exit(-1);
        }
        int[][] M1 = createMatrix(size);
        int[][] M2 = createMatrix(size);
        int[][] C = new int[size][size];
        OutputData outputData = new OutputData(C, "This is output data");

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < n; i++){
            InputData inputData = new InputData(M1, M2, size, n, i);
            ParallelMulInvoker parallelMulInvoker = new ParallelMulInvoker(inputData, outputData, startTime);
            parallelMulInvoker.start();
            System.out.println("Thread no " + i + " started");
            MainParallel m = new MainParallel();
            //delay put due to limits of AWS Lambda(1000 requests per second with a burst limit of 2000 rps)
            synchronized (m){
                m.wait(50);
            }
            if (i == n-1){
                long endTime = System.currentTimeMillis();
                System.out.println("The time of starting threads is " + (endTime - startTime));
                parallelMulInvoker.join();
                printMatrix(outputData.getMatrixC());
            }

        }
    }
    public static int[][] createMatrix(int size){
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                result[i][j] = 2;
            }
        }
        return result;
    }
    public  static void printMatrix(int[][] matrix){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                if (i == j) System.out.print(matrix[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
