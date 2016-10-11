package awsl_perfomance_test_wotransaction;

public class MainParallel1 {
    public static void main(String[] args) throws InterruptedException {
        int size = 4000;        //the size of matrix
        int n = 200;            //number of concurrent functions
        if (!checkInput(size, n)){
            System.out.println("Wrong input");
            System.exit(-1);
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < n; i++){
            //create input object for function
            InputData inputData = new InputData(size, n, i);
            //start a certain thread
            ParallelInvokerThread parallelInvokerThread = new ParallelInvokerThread(inputData);
            parallelInvokerThread.start();
            System.out.println("Thread no " + i + " started");
            MainParallel1 m = new MainParallel1();
            //delay put due to limits of AWS Lambda(1000 requests per second with a burst limit of 2000 rps)
            synchronized (m){
                m.wait(200);
            }
            if (i == n-1){
                parallelInvokerThread.join();
                long endTime = System.currentTimeMillis();
                System.out.println("The time for starting threads is " + (endTime - startTime));
            }
        }
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
}
